package com.zjw.myframe.web.utils.excel;

import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author Jianwen Zhu
 * @date 2017-02-10
 */
public class ExcelExportUtil {

    /**
     * 方法用途: 输出Excel工作簿<br>
     * 实现步骤: <br>
     *
     * @param request  请求对象
     * @param response 回复对象
     * @param workbook 工作簿
     * @param fileName 文件名
     *
     * @throws UnsupportedEncodingException 字符编码异常
     * @throws IOException                  IO异常
     */
    private static void export(HttpServletRequest request, HttpServletResponse response, Workbook workbook, String fileName) throws IOException{

        OutputStream out = response.getOutputStream();
        response.reset();

        String outFileName;
        if(request.getHeader("BaseUserBean-Agent").contains("MSIE")){
            outFileName = URLEncoder.encode(fileName, "UTF-8");
        }else{
            outFileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
        }
        response.setContentType("application/msexcel");
        response.setHeader("Content-disposition", "attachment; filename=" + outFileName);

        workbook.write(out);
    }

    /**
     * 方法用途: <br>
     * 实现步骤: <br>
     *
     * @param request      请求对象
     * @param response     回复对象
     * @param templateName Excel模板名称
     * @param fileName     导出文件名
     * @param beanParams   导出数据
     *
     * @throws Exception 异常
     */
    public static void exportExcelToWeb(HttpServletRequest request, HttpServletResponse response, String templateName, String fileName, Map<String, Object> beanParams) throws Exception{

        XLSTransformer transformer = new XLSTransformer();
        String realPath = request.getSession().getServletContext().getRealPath("/excel");
        InputStream in = new FileInputStream(realPath + File.separator + templateName);
        String suffix = "";

        if(!in.markSupported()){
            in = new PushbackInputStream(in, 8);
        }
        if(POIFSFileSystem.hasPOIFSHeader(in)){
            suffix = ".xls";
        }else if(POIXMLDocument.hasOOXMLHeader(in)){
            suffix = ".xlsx";
        }

        Workbook workbook = transformer.transformXLS(in, beanParams);

        export(request, response, workbook, fileName + suffix);
    }

    /**
     * 方法用途: <br>
     * 实现步骤: <br>
     *
     * @param templateName Excel模板名称
     * @param fileName     导出文件名
     * @param beanParams   导出数据
     *
     * @throws Exception 异常
     */
    public static void exportExcelToLocal(String templateName, String fileName, Map<String, Object> beanParams) throws Exception{

        InputStream templateIn = new FileInputStream(templateName);
        if(!templateIn.markSupported()){
            templateIn = new PushbackInputStream(templateIn, 8);
        }
        Workbook workbook = new XLSTransformer().transformXLS(templateIn, beanParams);

        File outFile = new File(fileName);
        if(!outFile.exists()){
            outFile.createNewFile();
        }
        workbook.write(new FileOutputStream(outFile));
    }

}
