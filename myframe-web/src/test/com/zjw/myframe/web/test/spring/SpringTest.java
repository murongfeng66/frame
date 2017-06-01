package com.zjw.myframe.web.test.spring;

import com.zjw.myframe.common.util.ReflectUtils;
import com.zjw.myframe.dao.bean.baseUser.BaseUserListBean;
import com.zjw.myframe.manager.baseUser.BaseUserManager;
import com.zjw.myframe.model.baseUser.BaseUser;
import com.zjw.myframe.web.utils.excel.ExcelExportUtil;
import com.zjw.myframe.web.utils.poi.ExcelUtil;
import org.apache.commons.collections.map.HashedMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * Spring测试类
 *
 * @author Jianwen Zhu
 * @version 1.0
 * @date 2016年7月28日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/spring-web.xml"})
@Transactional
@Rollback
public class SpringTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private BaseUserManager baseUserManager;
    @Test
    public void test() throws Exception {
        BaseUserListBean baseUserListBean = new BaseUserListBean();
        baseUserManager.queryByParam(baseUserListBean);
//        ExcelUtil<BaseUser> excelUtil = new ExcelUtil<>();
//        excelUtil.setFileName("基础用户列表");
//        excelUtil.setFilePath("E:\\");
//        excelUtil.setResult(baseUserListBean.getData());
//        excelUtil.setWeb(false);
//        excelUtil.addColumn("ID", "id");
//        excelUtil.addColumn("用户名", "username");
//        excelUtil.addColumn("用户类型", "typeMessage");
//        excelUtil.addColumn("状态", "statusMessage");
//        excelUtil.exportExcel();

        HashMap<String, Object> map = new HashMap<>();
        map.put("list", baseUserListBean.getData());
        ExcelExportUtil.exportExcelToLocal("E:\\baseUserTemplate.xls", "E:\\baseUser.xls", map);
    }

}
