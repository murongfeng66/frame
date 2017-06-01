package com.zjw.myframe.web.utils.poi;

import com.zjw.myframe.common.exception.SystemException;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * <p>Description: EXCEL导出工具基础类</p>
 *
 * @author Jianwen Zhu
 * @version 1.0
 *          <p>Company:XiPinTech</p>
 *          <p>Copyright:Copyright(c)2015</p>
 * @date 2016年5月4日
 */
public class ExcelBase<T> extends ExportParameter<T> {

    /**
     * Long正则表达式
     */
    public static final String LONG_MATCHES_STRING = "^-?\\d+$";
    /**
     * Double正则表达式
     */
    public static final String DOUBLE_MATCHES_STRING = "^-?\\d+(\\.\\d+)?$";
    /**
     * 临时文件根目录
     */
    private String tempFilePath;
    /**
     * 工作簿
     */
    protected HSSFWorkbook workbook;
    /**
     * 工作表
     */
    protected HSSFSheet sheet;
    /**
     * 画图管理器
     */
    protected HSSFPatriarch patriarch;
    /**
     * 当前行号
     */
    protected int rowIndex = 0;
    /**
     * 样式集合
     */
    private Map<String, CellStyle> cellStyleMap;
    /**
     * 工作表的数量
     */
    private int sheetSize = 0;
    /**
     * 临时文件集合
     */
    private LinkedList<File> fileList = new LinkedList<>();

    /**
     * 方法用途: 新增工作表<br>
     * 如果单个工作簿中工作表数量超过最大值{@linkplain #MAX_SHEET}，则将该工作簿写到临时文件中，并新建工作簿<br>
     * 实现步骤: <br>
     */
    protected void addSheet() throws IOException {
        if (sheetSize != 0 && sheetSize % MAX_SHEET == 0) {
            writeFile();
            initWorkbook();
        }

        sheetSize++;
        sheet = workbook.createSheet(fileName + sheetSize);
        sheet.setDefaultColumnWidth(columnWidth);
        sheet.setDefaultRowHeight(rowHeight);
        patriarch = sheet.createDrawingPatriarch();
        cellStyleMap = createCellStyleMap();
        rowIndex = 0;
        setTitle(fileName);
        setHeader();
    }

    /**
     * 方法用途: 将工作簿写入临时文件<br>
     * 实现步骤: <br>
     */
    private void writeFile() throws IOException {
        String filePath = tempFilePath + File.separator + fileName + (fileList.size() + 1) + ".xls";
        File file = new File(filePath);
        if (!file.createNewFile()) {
            throw new SystemException("创建临时文件失败：" + filePath);
        }
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
        workbook.write(out);
        out.close();
        fileList.add(file);
    }

    /**
     * 方法用途: 初始化工作簿<br>
     * 并以当前线程ID新建临时文件夹<br>
     * 实现步骤: <br>
     */
    protected void initWorkbook() {
        workbook = new HSSFWorkbook();
        if (isWeb && StringUtils.isEmpty(tempFilePath)) {
            tempFilePath = request.getServletContext().getRealPath("/") + "temp" + File.separator + Thread.currentThread().getId();
        }
        if(!isWeb && StringUtils.isEmpty(tempFilePath)){
            tempFilePath = filePath + "temp" + File.separator + Thread.currentThread().getId();
        }
        File file = new File(tempFilePath);
        if (file.exists()) {
            if (!file.delete()) {
                throw new SystemException("删除临时文件夹失败：" + tempFilePath);
            }
        }
        if (!file.mkdirs()) {
            throw new SystemException("创建临时文件夹失败：" + tempFilePath);
        }
    }

    /**
     * 方法用途: 获取表头样式<br>
     * 水平居中 细边框，黑色 填充蓝色<br>
     * 实现步骤: <br>
     *
     * @return 表头样式
     */
    private HSSFCellStyle getHeaderCellStyle() {
        HSSFCellStyle cellStyle = workbook.createCellStyle();

        // 设置表头样式
        cellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 设置标题字体
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        cellStyle.setFont(font);

        // 指定当单元格内容显示不下时自动换行
        cellStyle.setWrapText(true);

        return cellStyle;
    }

    /**
     * 方法用途: 获取标题样式<br>
     * 水平居中、垂直居中 字体：15，粗体<br>
     * 实现步骤: <br>
     */
    private HSSFCellStyle getTitlecCellStyle() {
        HSSFCellStyle cellStyle = workbook.createCellStyle();

        // 设置标题样式
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 设置字体
        HSSFFont titleFont = workbook.createFont();
        titleFont.setFontName("宋体");
        titleFont.setFontHeightInPoints((short) 20);
        titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        cellStyle.setFont(titleFont);

        return cellStyle;
    }

    /**
     * 方法用途: 获取合计样式<br>
     * 字体：10，粗体<br>
     * 实现步骤: <br>
     */
    protected HSSFCellStyle getCountCellStyle() {
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        HSSFFont titleFont = workbook.createFont();
        titleFont.setFontHeightInPoints((short) 10);
        titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        cellStyle.setFont(titleFont);
        return cellStyle;
    }

    /**
     * 方法用途: 设置标题行<br>
     * 实现步骤: <br>
     *
     * @param titel 标题
     */
    private void setTitle(String titel) {
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headers.size() - 1));
        HSSFRow row = sheet.createRow(0);
        row.setHeight((short) 500);
        HSSFCell cellHeader = row.createCell(0);
        cellHeader.setCellStyle(getTitlecCellStyle());
        cellHeader.setCellValue(titel);
        rowIndex++;
    }

    /**
     * 方法用途: 设置表头<br>
     * 实现步骤: <br>
     */
    private void setHeader() {
        HSSFRow row = sheet.createRow(1);
        for (int i = 0; i < headers.size(); i++) {
            HSSFCell cell = row.createCell(i);
            sheet.setColumnWidth(i, (headers.get(i).length() / 4 + 1) * 2560);
            cell.setCellStyle(getHeaderCellStyle());
            cell.setCellValue(headers.get(i));
        }
        rowIndex++;
    }

    public void outWorkbook() throws Exception {
        if (isWeb) {
            outWorkbookToWeb();
        } else {
            outWorkbookToLocal();
        }

        // 删除临时文件
        deleteFile(tempFilePath);
    }

    /**
     * 方法用途: 输出工作簿至WEB<br>
     *
     * @throws Exception 异常
     */
    private void outWorkbookToWeb() throws Exception {
        OutputStream out = response.getOutputStream();
        response.reset();

        String outFileName;
        if (request.getHeader("BaseUserBean-Agent").contains("MSIE")) {
            outFileName = java.net.URLEncoder.encode(fileName, "UTF-8");
        } else {
            outFileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
        }
        if (fileList.size() == 0) {
            response.setContentType("application/msexcel");
            response.setHeader("Content-disposition", "attachment; filename=" + outFileName + ".xls");

            workbook.write(out);
        } else {
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + outFileName + ".zip");
            // 输出最后一张工作簿
            writeFile();

            String zipFilePath = tempFilePath + File.separator + fileName + ".zip";
            createZipFile(zipFilePath);

            // 输出ZIP文件
            byte[] buffer = new byte[1024];
            int len;
            FileInputStream zipIn = new FileInputStream(zipFilePath);
            while ((len = zipIn.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            zipIn.close();
        }
    }

    /**
     * 方法用途: 输出工作簿至本地<br>
     *
     * @throws Exception 异常
     */
    private void outWorkbookToLocal() throws Exception {
        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File outFile = new File(filePath + fileName + ".xls");
        if (!outFile.exists()) {
            outFile.createNewFile();
        }
        FileOutputStream out = new FileOutputStream(outFile);

        if (fileList.size() == 0) {
            workbook.write(out);
        } else {
            // 输出最后一张工作簿
            writeFile();

            String zipFilePath = tempFilePath + File.separator + fileName + ".zip";
            createZipFile(zipFilePath);

            // 输出ZIP文件
            byte[] buffer = new byte[1024];
            int len;
            FileInputStream zipIn = new FileInputStream(zipFilePath);
            while ((len = zipIn.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            zipIn.close();
        }
    }

    /**
     * 方法用途: 将临时文件打包成ZIP压缩包<br>
     * 实现步骤: <br>
     *
     * @param zipFilePath 压缩包路径
     */
    private void createZipFile(String zipFilePath) throws IOException {
        ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFilePath));
        byte[] buffer = new byte[1024];
        int len;
        for (File file : fileList) {
            FileInputStream fileIn = new FileInputStream(file);
            zipOut.putNextEntry(new ZipEntry(file.getName()));
            while ((len = fileIn.read(buffer)) > 0) {
                zipOut.write(buffer, 0, len);
            }
            zipOut.closeEntry();
            fileIn.close();
        }
        zipOut.close();
    }

    /**
     * 方法用途: 删除文件或文件夹<br>
     * 实现步骤: <br>
     *
     * @param filePath 文件或文件夹路径
     */
    private void deleteFile(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            File delFile[] = file.listFiles();
            if (delFile != null) {
                if (delFile.length == 0) {
                    file.delete();
                } else {
                    for (File fileItem : delFile) {
                        if (fileItem.isDirectory()) {
                            deleteFile(fileItem.getAbsolutePath());
                        }
                        fileItem.delete();
                    }
                }
            }
        }
        file.delete();
    }

    /**
     * 方法用途: 将值对象格式化为字符串<br>
     * 实现步骤: <br>
     *
     * @param value       值对象
     * @param columnIndex 列标
     * @return 值对象字符串
     */
    protected String getValueString(Object value, int columnIndex) {
        String valueString = "";
        if (value == null) {
            return valueString;
        } else if (value instanceof Date) {
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            valueString = sdf.format((Date) value);
        } else if (value instanceof byte[]) {
            // 有图片时，设置行高为60px;
            HSSFRow row = sheet.getRow(rowIndex);
            row.setHeightInPoints(60);
            // 设置图片所在列宽度为80px,注意这里单位的一个换算
            sheet.setColumnWidth(columnIndex, (short) (35.7 * 80));
            byte[] valueByte = (byte[]) value;
            HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 255, (short) 6, rowIndex, (short) 6, rowIndex);
            anchor.setAnchorType(2);
            patriarch.createPicture(anchor, workbook.addPicture(valueByte, HSSFWorkbook.PICTURE_TYPE_JPEG));
        } else if (value instanceof String[]) {
            //字符串数组
            for (String string : (String[]) value) {
                valueString = valueString + string + ",";
            }
        } else {
            valueString = value.toString();
        }
        return valueString;
    }

    /**
     * 方法用途: 获取值对象<br>
     * 实现步骤: <br>
     *
     * @param resultItem 行数据
     * @param fieldName  值名称
     * @return 值对象
     * @throws Exception 异常
     */
    protected Object getValueObject(Object resultItem, String fieldName) throws Exception {
        Object value;
        if (resultItem instanceof Map) {
            value = ((Map<?, ?>) resultItem).get(fieldName);
        } else {
            Class<?> resultItemClass = resultItem.getClass();
            String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            Method getMethod = resultItemClass.getMethod(getMethodName);
            value = getMethod.invoke(resultItem);
        }
        return value;
    }

    /**
     * 方法用途: 设置单元格值<br>
     * 实现步骤: <br>
     *
     * @param valueString 单元格值
     * @param cell        单元格
     */
    protected void setCellValue(String valueString, Cell cell) {
        if (StringUtils.isEmpty(valueString)) {
            cell.setCellStyle(getCellStyle("stringCellStyle"));
            return;
        }
        if (valueString.matches(LONG_MATCHES_STRING)) {
            cell.setCellStyle(getCellStyle("longCellStyle"));
            cell.setCellValue(Long.valueOf(valueString));
        } else if (valueString.matches(DOUBLE_MATCHES_STRING)) {
            cell.setCellStyle(getCellStyle("doubleCellStyle"));
            cell.setCellValue(Double.valueOf(valueString));
        } else {
            cell.setCellStyle(getCellStyle("stringCellStyle"));
            cell.setCellValue(valueString);
        }
    }

    /**
     * 方法用途: 创建单元格格式集合<br>
     * 实现步骤: <br>
     */
    private Map<String, CellStyle> createCellStyleMap() {
        Map<String, CellStyle> cellStyleMap = new HashMap<>();

        CellStyle longCellStyle = createCellStyle();
        longCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));
        cellStyleMap.put("longCellStyle", longCellStyle);

        CellStyle doubleCellStyle = createCellStyle();
        doubleCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
        cellStyleMap.put("doubleCellStyle", doubleCellStyle);

        CellStyle stringCellStyle = createCellStyle();
        stringCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
        cellStyleMap.put("stringCellStyle", stringCellStyle);

        return cellStyleMap;
    }

    /**
     * 获取通用单元格
     */
    private CellStyle createCellStyle() {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        return cellStyle;
    }

    /**
     * 方法用途: 获取单元格格式<br>
     * 如果没有抛出空指针异常<br>
     * 实现步骤: <br>
     *
     * @param styleName 格式名称
     * @return 单元格样式
     */
    protected CellStyle getCellStyle(String styleName) {
        if (cellStyleMap.containsKey(styleName)) {
            return cellStyleMap.get(styleName);
        } else {
            throw new NullPointerException(styleName + "is null");
        }
    }

    /**
     * 方法用途: 判断对象属性或集合中是否存在该元素<br>
     * 实现步骤: <br>
     *
     * @param t         对象或者集合
     * @param fieldName 元素
     * @return 判断结果
     */
    protected boolean hasField(T t, Object fieldName) {
        if (t instanceof Map) {
            if (((Map<?, ?>) t).containsKey(fieldName)) {
                return true;
            }
        } else {
            Field[] fields = t.getClass().getFields();
            for (Field field : fields) {
                if (field.getName().equals(fieldName)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 方法用途: 创建数据行<br>
     * 当数据行超过行最大值时，新建一个工作表
     * 实现步骤: <br>
     *
     * @return 新数据行
     */
    protected HSSFRow createRow() throws IOException {
        if (rowIndex >= MAX_ROW) {
            addSheet();
        }
        HSSFRow row = sheet.createRow(rowIndex);
        row.setHeight((short) 400);
        return row;
    }

}
