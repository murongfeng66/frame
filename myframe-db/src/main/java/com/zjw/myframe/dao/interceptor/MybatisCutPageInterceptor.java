package com.zjw.myframe.dao.interceptor;

import com.zjw.myframe.common.exception.SystemException;
import com.zjw.myframe.common.util.ReflectUtils;
import com.zjw.myframe.dao.bean.PageBean;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.jdbc.ConnectionLogger;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.transaction.Transaction;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

/**
 * MYBATIS 分页插件
 *
 * @author Jianwen Zhu
 * @date 2017-02-11
 */
@Intercepts({@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class MybatisCutPageInterceptor implements Interceptor {

    private HashSet<String> sqlNames = new HashSet<>();
    private DataBaseType dataBaseType;
    private String countSQLSuffix;

    private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource){
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        if(ms.getKeyProperties() != null){
            for(String keyProperty : ms.getKeyProperties()){
                builder.keyProperty(keyProperty);
            }
        }
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(ms.getResultMaps());
        builder.cache(ms.getCache());
        return builder.build();
    }

    /**
     * 进行切分
     */
    @SuppressWarnings("unchecked")
    private Object doCutPage(Invocation invocation, Object parameter, PageBean<Object> pageBean) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException{
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Configuration configuration = mappedStatement.getConfiguration();
        BoundSql boundSql = mappedStatement.getBoundSql(pageBean);
        if(sqlNames.isEmpty()){
            this.initStatementMap(configuration);
        }

        pageBean.setCount(this.getCount(invocation, configuration, mappedStatement, boundSql, pageBean));

        String limitSql = getLimitSql(boundSql, pageBean);
        BoundSql dataBoundSql = new BoundSql(mappedStatement.getConfiguration(), limitSql, boundSql.getParameterMappings(), boundSql.getParameterObject());
        if(ReflectUtils.getFieldValue(boundSql, "metaParameters") != null){
            MetaObject mo = (MetaObject) ReflectUtils.getFieldValue(boundSql, "metaParameters");
            ReflectUtils.setFieldValue(dataBoundSql, "metaParameters", mo);
        }
        MappedStatement DataMappedStatement = copyFromMappedStatement(mappedStatement, new BoundSqlSqlSource(dataBoundSql));
        invocation.getArgs()[0] = DataMappedStatement;
        Object object = invocation.proceed();
        if(object instanceof List){
            pageBean.setData((List<Object>) object);
        }
        return object;
    }

    /**
     * 执行统计SQL
     */
    private int exeCountSql(Configuration configuration, MappedStatement mappedStatement, BoundSql boundSql, Connection connection, Object parameter) throws SQLException{
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int totalSize = 0;
        try{
            ParameterHandler handler = configuration.newParameterHandler(mappedStatement, parameter, boundSql);
            stmt = connection.prepareStatement(this.getCountSql(boundSql.getSql()));
            handler.setParameters(stmt);
            rs = stmt.executeQuery();
            if(rs.next()){
                totalSize = rs.getInt(1);
            }
        }finally{
            if(rs != null){
                rs.close();
            }
            if(stmt != null){
                stmt.close();
            }
        }
        return totalSize;
    }

    /**
     * 执行查询
     */
    private Object exeProceed(Invocation invocation, MappedStatement statement) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        invocation.getArgs()[0] = statement;
        return invocation.proceed();
    }

    /**
     * 获取数据库连接
     */
    private Connection getConnection(Transaction transaction, Log statementLog) throws SQLException{
        Connection connection = transaction.getConnection();
        if(statementLog.isDebugEnabled()){
            return ConnectionLogger.newInstance(connection, statementLog, 1);
        }else{
            return connection;
        }
    }

    /**
     * 获取结果总数
     */
    private int getCount(Invocation invocation, Configuration configuration, MappedStatement statement, BoundSql boundSql, Object parameter) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException{
        int count = 0;
        String countSqlId = statement.getId() + countSQLSuffix;
        if(sqlNames.contains(countSqlId)){
            List<?> data = (List<?>) exeProceed(invocation, statement.getConfiguration().getMappedStatement(countSqlId));
            if(data.size() > 0){
                count = Integer.parseInt(data.get(0).toString());
            }
        }else{
            Executor ext = (Executor) invocation.getTarget();
            Connection conn = getConnection(ext.getTransaction(), statement.getStatementLog());
            count = exeCountSql(configuration, statement, boundSql, conn, parameter);
        }
        return count;
    }

    /**
     * 获取统计语句
     */
    private String getCountSql(String sql){
        return "SELECT COUNT(1) FROM (" + sql + ") T";
    }

    /**
     * 获取切分SQL
     */
    private String getLimitSql(BoundSql boundSql, PageBean<?> pageBean){
        if(!pageBean.getIsCut()){
            return boundSql.getSql();
        }
        return dataBaseType.getLimitSql(boundSql.getSql(), pageBean);
    }

    /**
     * 获取分页参数
     */
    @SuppressWarnings("unchecked")
    private PageBean<Object> getPageBean(Object[] parameter){
        for(Object obj : parameter){
            if(obj instanceof PageBean){
                return ((PageBean<Object>) obj);
            }
        }
        return null;
    }

    /**
     * 初始化SQL语句
     */
    private synchronized void initStatementMap(Configuration configuration){
        if(!sqlNames.isEmpty()){
            return;
        }
        Collection<String> statements = configuration.getMappedStatementNames();
        for(String name : statements){
            sqlNames.add(name);
        }
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable{
        Object[] parameter = invocation.getArgs();
        PageBean<Object> page = getPageBean(parameter);
        if(page == null){
            return invocation.proceed();
        }else{
            return this.doCutPage(invocation, parameter, page);
        }
    }

    @Override
    public Object plugin(Object target){
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties){
        String dataBaseType = properties.getProperty("dataBaseType");
        this.dataBaseType = DataBaseType.initDataBaseType(dataBaseType);
        this.countSQLSuffix = properties.getProperty("countSQLSuffix");
        this.countSQLSuffix = this.countSQLSuffix == null ? "Count" : this.countSQLSuffix;
    }

    private enum DataBaseType {
        ORACLE {
            @Override
            public String getLimitSql(String sql, PageBean<?> pageBean){
                StringBuilder pageSql = new StringBuilder();
                String beginrow = String.valueOf((pageBean.getPage() - 1) * pageBean.getPageSize());
                String endrow = String.valueOf(pageBean.getPage() * pageBean.getPageSize());
                pageSql.append("select * from ( select temp.*, rownum row_id from ( ");
                pageSql.append(sql);
                pageSql.append(" ) temp where rownum <= ").append(endrow);
                pageSql.append(") where row_id > ").append(beginrow);
                return pageSql.toString();
            }
        }, MYSQL {
            @Override
            public String getLimitSql(String sql, PageBean<?> pageBean){
                StringBuilder pageSql = new StringBuilder();
                String beginrow = String.valueOf((pageBean.getPage() - 1) * pageBean.getPageSize());
                pageSql.append(sql);
                pageSql.append(" limit ").append(beginrow).append(",").append(pageBean.getPageSize());
                return pageSql.toString();
            }
        };

        /**
         * 初始化数据库类型
         */
        public static DataBaseType initDataBaseType(String dataBaseType){
            if("ORACLE".equalsIgnoreCase(dataBaseType)){
                return ORACLE;
            }else if("MYSQL".equalsIgnoreCase(dataBaseType)){
                return MYSQL;
            }else{
                throw new SystemException("不被支持的数据库类型");
            }
        }

        /**
         * 获取切分语句
         */
        public abstract String getLimitSql(String sql, PageBean<?> pageBean);
    }

    public static class BoundSqlSqlSource implements SqlSource {

        BoundSql boundSql;

        BoundSqlSqlSource(BoundSql boundSql){
            this.boundSql = boundSql;
        }

        @Override
        public BoundSql getBoundSql(Object parameterObject){
            return boundSql;
        }
    }
}
