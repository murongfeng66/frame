package com.zjw.myframe.web.utils.poi;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * EXCEL导出工具(POI)
 *
 * @author Jianwen Zhu
 * @date 2017-02-10
 */
public class ExcelUtil<T> extends ExcelBase<T> {

    /**
     * 一维集合导出，普通
     *
     */
    public void exportExcel() throws Exception{
        validParameter();
        initWorkbook();
        addSheet();

        // 合计列计算值
        Map<Integer, Double> totalColumnValues = new HashMap<>();
        HSSFRow row;
        // 遍历集合数据，产生数据行
        for(T resultItem : result){
            row = createRow();
            for(int columnIndex = 0; columnIndex < columns.size(); columnIndex++){
                HSSFCell cell = row.createCell(columnIndex);
                String fieldName = columns.get(columnIndex);

                Object valueObject = getValueObject(resultItem, fieldName);
                String valueString = getValueString(valueObject, columnIndex);

                setCellValue(valueString, cell);
                if(StringUtils.isEmpty(valueString)){
                    continue;
                }

                // 计算合计列的值
                if(!totalColumns.contains(fieldName)){
                    continue;
                }
                // 验证该列是否有非数字值，有则从合计列中移除，空值不作为非数字值。
                if(!valueString.matches(DOUBLE_MATCHES_STRING)){
                    totalColumns.remove(fieldName);
                    continue;
                }
                Double valueDouble = Double.valueOf(valueString);
                if(totalColumnValues.containsKey(columnIndex)){
                    Double tempDouble = totalColumnValues.get(columnIndex);
                    tempDouble = tempDouble + valueDouble;
                    totalColumnValues.put(columnIndex, tempDouble);
                }else{
                    totalColumnValues.put(columnIndex, valueDouble);
                }
            }
            rowIndex++;
        }

        // 设置合计列的值
        if(CollectionUtils.isNotEmpty(totalColumns)){
            row = createRow();

            HSSFCell cell = row.createCell(0);
            cell.setCellStyle(getCountCellStyle());
            cell.setCellValue("合计：");
            for(String totalColumn : totalColumns){
                int totalIndex = totalColumns.indexOf(totalColumn);
                cell = row.createCell(totalIndex);
                cell.setCellStyle(getCellStyle("doubleCellStyle"));
                cell.setCellValue(totalColumnValues.get(totalIndex) == null ? 0 : totalColumnValues.get(totalIndex));
            }
        }

        outWorkbook();

    }

    /**
     * 二维集合导出，合并
     *
     * @param collectionName 一维集合中二维集合的列名
     * @param firstColumns   一维集合列名，二维集合中的列名使用""替代，二维集合第一列列名使用collectionName参数值替代
     * @param secondColumns  二维集合列名
     *
     * @throws Exception 抛出异常
     */
    @SuppressWarnings("unchecked")
    public <V> void exportExcelForVerticalMerger(String collectionName, String[] firstColumns, String[] secondColumns) throws Exception{
        validParameter();
        initWorkbook();
        addSheet();

        // 遍历一维集合
        HSSFRow row;
        for(T firstResultItem : result){
            row = createRow();
            // 计算合并域
            int rowBegin = rowIndex; int rowEnd = rowIndex + ((Collection<V>) getValueObject(firstResultItem, collectionName)).size() - 1;

            for(int columnIndex = 0; columnIndex < firstColumns.length; columnIndex++){
                String fieldName = firstColumns[columnIndex];
                if(fieldName.equals(collectionName)){// 是二维集合元素
                    Collection<V> secondResult = (Collection<V>) getValueObject(firstResultItem, collectionName);
                    Iterator<V> it = secondResult.iterator();
                    // 遍历二维集合
                    while(it.hasNext()){
                        V secondResultItem = it.next();
                        for(int j = 0; j < secondColumns.length; j++){
                            HSSFCell secondCell = row.createCell(columnIndex + j);
                            fieldName = secondColumns[j];

                            Object valueObject = getValueObject(secondResultItem, fieldName);
                            String valueString = getValueString(valueObject, columnIndex + j);

                            setCellValue(valueString, secondCell);
                        }
                        // 如果是二维集合非最后一行，则由二维集合移动行标，否则由外层移动行标。
                        if(it.hasNext()){
                            rowIndex++;
                            row = sheet.createRow(rowIndex);
                        }
                    }

                    // 移动列标至二维集合列后一列
                    columnIndex = columnIndex + secondColumns.length - 1;
                }else{// 普通元素
                    sheet.addMergedRegion(new CellRangeAddress(rowBegin, rowEnd, columnIndex, columnIndex));
                    HSSFCell firstCell = sheet.getRow(rowBegin).createCell(columnIndex);

                    Object valueObject = getValueObject(firstResultItem, fieldName);
                    String valueString = getValueString(valueObject, columnIndex);

                    setCellValue(valueString, firstCell);
                }
            }
            rowIndex++;
        }

        outWorkbook();
    }

    /**
     * 二维集合导出，动态列
     *
     * @param headerIndex    动态列开始的列索引
     * @param columnKey      动态列表头对应的属性名
     * @param valueKey       动态列值对应的属性名
     * @param collectionName 一维集合中二维集合的列名
     *
     * @throws Exception 抛出异常
     */
    @SuppressWarnings("unchecked")
    public <V> void exportExcelDynamicColumn(int headerIndex, String columnKey, String valueKey, String collectionName) throws Exception{
        validParameter();
        // 构造动态表头
        for(T firstResultItem : result){
            Collection<V> secondResult = (Collection<V>) getValueObject(firstResultItem, collectionName);
            for(V secondResultItem : secondResult){
                String headerTemp = getValueObject(secondResultItem, columnKey).toString();
                // 如果没有该列，则添加该列
                if(!headers.contains(headerTemp)){
                    // 添加表头列名
                    headers.add(headerIndex, headerTemp);
                    // 添加列标索引
                    columns.add(headerIndex, headerTemp);
                }
            }
        }

        initWorkbook();
        addSheet();

        HSSFRow row;
        for(T firstResultItem : result){
            boolean isWrite = false;
            row = createRow();
            for(int columnIndex = 0; columnIndex < columns.size(); columnIndex++){
                String fieldName = columns.get(columnIndex);
                // 如果一维集合中有该元素则从一维集合取值，否则从二维集合取值（动态列）
                if(hasField(firstResultItem, fieldName)){
                    HSSFCell cell = row.createCell(columnIndex);
                    Object valueObject = getValueObject(firstResultItem, fieldName);

                    String valueString = getValueString(valueObject, columnIndex);

                    setCellValue(valueString, cell);
                }else if(!isWrite){
                    // 防止同一一维集合下的二维集合被重复设值
                    isWrite = true;
                    Collection<V> secondResult = (Collection<V>) getValueObject(firstResultItem, collectionName);
                    // 动态列设值
                    for(V secondResultItem : secondResult){
                        // 获取到对应的列标
                        String headerTemp = getValueObject(secondResultItem, columnKey).toString();
                        columnIndex = headers.indexOf(headerTemp);

                        HSSFCell cell = row.createCell(columnIndex);
                        Object valueObject = getValueObject(secondResultItem, valueKey);
                        String valueString = getValueString(valueObject, columnIndex);
                        setCellValue(valueString, cell);
                    }
                }
            }
            rowIndex++;
        }

        outWorkbook();
    }

}