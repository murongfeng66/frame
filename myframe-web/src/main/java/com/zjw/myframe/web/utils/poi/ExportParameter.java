package com.zjw.myframe.web.utils.poi;

import com.zjw.myframe.common.exception.SystemParamException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;

/**
 * EXCEL POI导出参数类
 *
 * @author Jianwen Zhu
 */
public class ExportParameter<T> {

    /**
     * 单个工作表最大行数<br>
     * 默认：5000
     */
    public final int MAX_ROW = 5000;
    /**
     * 单个工作表最大工作表数<br>
     * 默认：5
     */
    public final int MAX_SHEET = 5;
    /**
     * 列宽<br>
     * 默认：10
     */
    protected int columnWidth = 10;
    /**
     * 行高<br>
     * 默认：20
     */
    protected short rowHeight = 400;
    /**
     * 时间格式<br>
     * 默认：yyyy-MM-dd HH:mm:ss
     */
    protected String dateFormat = "yyyy-MM-dd HH:mm:ss";
    /**
     * 统计列
     */
    protected List<String> totalColumns = new LinkedList<>();
    /**
     * 请求对象
     */
    protected HttpServletRequest request;
    /**
     * 回应对象
     */
    protected HttpServletResponse response;
    /**
     * 是否是WEB模式<br>
     * 默认：true<br>
     * 当为true时，将通过WEB形式输出导出文件，此时 {@linkplain #filePath} 值不能为null<br>
     * 否则将文件存储在服务器本地，此时 {@linkplain #request} 和 {@linkplain #response} 值不能为null
     */
    protected boolean isWeb = true;
    /**
     * 本地文件目录
     */
    protected String filePath;
    /**
     * 表头
     */
    protected List<String> headers = new LinkedList<>();
    /**
     * 列名
     */
    protected List<String> columns = new LinkedList<>();
    /**
     * 文件名称
     */
    protected String fileName;
    /**
     * 结果集
     */
    protected Collection<T> result;

    /**
     * 验证参数合法性
     */
    protected void validParameter() {
        if (isWeb && request == null) {
            throw new SystemParamException("请求对象不能为空");
        }
        if (isWeb && response == null) {
            throw new SystemParamException("回应对象不能为空");
        }
        if (!isWeb && filePath.isEmpty()) {
            throw new SystemParamException("本地文件目录不能为空");
        }
        if (headers.isEmpty()) {
            throw new SystemParamException("表头不能为空");
        }
        if (columns.isEmpty()) {
            throw new SystemParamException("列名不能为空");
        }
        if (headers.size() != columns.size()) {
            throw new SystemParamException("表头和列名不匹配");
        }
        if (fileName.isEmpty()) {
            throw new SystemParamException("文件名称不能为空");
        }
        if (result.isEmpty()) {
            throw new SystemParamException("结果集不能为空");
        }
    }

    /**
     * {@linkplain #columnWidth}
     */
    public void setColumnWidth(int columnWidth) {
        this.columnWidth = columnWidth;
    }

    /**
     * {@linkplain #rowHeight}
     */
    public void setRowHeight(short rowHeight) {
        this.rowHeight = rowHeight;
    }

    /**
     * {@linkplain #request}
     */
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * {@linkplain #response}
     */
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    /**
     * 添加表头<br>
     * {@linkplain #headers}
     */
    public void addHeader(String header) {
        this.headers.add(header);
    }

    /**
     * {@linkplain #fileName}
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * 添加列名<br>
     * {@linkplain #columns}
     */
    public void addColumn(String column) {
        this.columns.add(column);
    }

    /**
     * 添加列名<br>
     * {@linkplain #columns}
     */
    public void addColumn(String header, String column) {
        this.headers.add(header);
        this.columns.add(column);
    }

    /**
     * {@linkplain #result}
     */
    public void setResult(Collection<T> result) {
        this.result = result;
    }

    /**
     * {@linkplain #dateFormat}
     */
    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    /**
     * {@linkplain #dateFormat}
     */
    public void addTotalColumns(String totalColumns) {
        this.totalColumns.add(totalColumns);
    }

    /**
     * {@linkplain #isWeb}
     */
    public void setWeb(boolean web) {
        isWeb = web;
    }

    /**
     * {@linkplain #filePath}
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
