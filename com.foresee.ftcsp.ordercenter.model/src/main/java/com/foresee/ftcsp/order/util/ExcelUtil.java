package com.foresee.ftcsp.order.util;

import com.foresee.ftcsp.common.core.util.Loggers;
import com.foresee.ftcsp.order.auto.model.OrdExcelDataTemp;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

/**
 * <pre>
 * 解析,生成Excel工具类
 * </pre>
 *
 * @author liweixin@foresee.com.cn
 * @version 1.00.00
 *          <p>
 *          <pre>
 *          修改记录
 *          修改后版本:     修改人：  修改日期:     修改内容:
 *              </pre>
 * @date 2017/11/23
 */
public class ExcelUtil {

    Logger logger = Loggers.make();

    //存入表对应的字段名字集合
    private static ThreadLocal<Map<String,Integer>> columnName = new ThreadLocal<>();

    //存入传参dto中含有ExcelColumnName注解的字段
    private static ThreadLocal<Map<String,Field>> objColumn = new ThreadLocal<>();


    /**
     * 一次性读取所有
     * @param file
     * @param cls
     * @return
     * @throws Exception
     */
    public static <T> List<T> parseExcel(File file, Class cls) throws Exception{
        return parseExcel(file,cls,null,null);
    }


    /**
     *
     * @param Class cls 要转换的数据对象class,必须提供默认的构造方法，解析字段必须为String类型
     * @param File file 要解析的Excel文件对象
     * @param Integer startRow 从第几行开始解析
     * @param Integer startRow 解析到第几行
     * @return List<dto>
     */
    public static <T> List<T> parseExcel(File file, Class cls,Integer startRow,Integer endRow) throws Exception{
        List objList = new ArrayList();
        FileInputStream in = null;
        try {
            if(!file.canRead()){
                throw new IllegalArgumentException("the file can not be read or did not exist!");
            }
            in = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(in);
            Sheet sheet = workbook.getSheetAt(0); //默认只解析第一页表格页
            if(Objects.isNull(sheet)){
                throw new IllegalArgumentException("the excel is blank!");
            }
            int rowNumber = sheet.getPhysicalNumberOfRows();
            startRow =  startRow == null?1:startRow;
            endRow =  endRow == null?rowNumber:endRow;
            if(rowNumber<1){ //认为是空模板，第一行为模板字段标注
                throw new IllegalArgumentException("the excel did not has data!");
            }
            Row headRow = sheet.getRow(0);
            int minColumn = headRow.getFirstCellNum();
            int maxColumn = headRow.getLastCellNum();
            Map<String, Integer> columnMap = new HashMap<>();
            for (int i=minColumn;i<maxColumn;i++) {
                Cell cell = headRow.getCell(i);
                String value = getCellValue(cell);
                if (!StringUtils.isBlank(value)) {  //第一行模板说明必须不为空
                    columnMap.put(value, i);   //将字段值以及对应的列树记录下来
                }
            }
            if (!columnMap.isEmpty()){
                columnName.set(columnMap);
            }
            boolean flag = valide(cls);
            if (!flag) {
                throw new IllegalArgumentException("Excel标题行与模板类注解不相符合:"+cls.getName());
            }
            //开始执行解析并生成dto,从第一行开始解析

            for(int i = startRow;i<endRow;i++) {
                Row dataRow = sheet.getRow(i);
                if (Objects.isNull(dataRow)){
                    continue;
                }
                if( ifNullRow(dataRow) ) continue;
                Object obj = injectClass(cls,dataRow);
                objList.add(obj);
            }
            return objList;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (in != null) {
                in.close();
            }
        }
        return objList;
    }

    private static String getCellValue(Cell cell){
            String value = null;
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING://字符串
                    value = cell.getRichStringCellValue().getString();
                    break;
                case Cell.CELL_TYPE_NUMERIC://数字
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {  //日期格式
                        Date date = cell.getDateCellValue();
                        value = DateFormatUtils.format(date, "yyyy-MM-dd hh:mm");
                    } else {
                        double valueTmp = cell.getNumericCellValue();
                        long l = (long) valueTmp;
                        if(l==valueTmp){
                            DecimalFormat df = new DecimalFormat("0");
                            value = df.format(valueTmp);
                        }else {  //价格类型的数字
                            DecimalFormat df = new DecimalFormat("0.00");
                            df.setRoundingMode(RoundingMode.HALF_UP);
                            BigDecimal bd = BigDecimal.valueOf(valueTmp);
                            value = df.format(bd);
                        }
                    }
                    break;
                case Cell.CELL_TYPE_BLANK:
                    value = "";
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    try {
                        value = String.valueOf(cell.getRichStringCellValue());
                    } catch (IllegalStateException e) {
                        DecimalFormat df = new DecimalFormat("0");
                        double num = cell.getNumericCellValue();
                        value = df.format(num);
                    }
                    break;
                case Cell.CELL_TYPE_BOOLEAN://boolean型值
                    value = String.valueOf(cell.getBooleanCellValue());
                    break;
                case Cell.CELL_TYPE_ERROR:
                    value = String.valueOf(cell.getErrorCellValue());
                    break;
                default:
                    break;
            }
        return value;
    }


    public static boolean valide(Class cls) throws IllegalAccessException {
        Map<String,Field > annoValueSet = new HashMap<>();
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            ExcelColumnName annotation = field.getAnnotation(ExcelColumnName.class);
            if (annotation != null) {
                if(!field.getType().equals(String.class))
                    throw new IllegalAccessException("the excel value can only inject field with String type!");
                String value = annotation.value();
                annoValueSet.put(value, field);
            }
        }
        if (columnName.get().keySet().equals(annoValueSet.keySet())){ //若两个map的keyset是相等，说明Excel模板字段跟dto注解字段是一致的
            objColumn.set(annoValueSet);
            return true;
        }
        return false;
    }

    private static Object injectClass(Class cls,Row row) throws Exception{
        Object obj = cls.newInstance();
        Iterator<Map.Entry<String,Integer>> it = columnName.get().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String,Integer> entry = it.next();
            String headValue = entry.getKey();
            Integer cellNum = entry.getValue();
            String objFieldName = objColumn.get().get(headValue).getName();
            Class objFieldType = objColumn.get().get(headValue).getType();
            Method method = obj.getClass().getMethod("set" + objFieldName.substring(0,1).toUpperCase()+objFieldName.substring(1),objFieldType);
            Cell cell = row.getCell(cellNum);
            if(Objects.isNull(cell)) {
                method.invoke(obj,new Object[]{null});
            }else {
                method.invoke(obj, getCellValue(cell));
            }
        }
        return obj;
    }


    private static boolean ifNullRow(Row row){
        Map<String,Integer> headColumn = columnName.get();
        Iterator<Map.Entry<String,Integer>> it = headColumn.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String,Integer> entry = it.next();
            Integer cellNum = entry.getValue();
            Cell cell = row.getCell(cellNum);
            if(Objects.isNull(cell)) continue;
            String value = getCellValue(cell);
            if(!StringUtils.isBlank(value)) return false;
        }
        return true;
    }

    public static Integer getExcelRowNum(File file){
        String fileName = file.getName();
        FileInputStream fis = null;
        Workbook workbook = null;
        Integer count = null;
        try {
            fis = new FileInputStream(file);
             workbook = new XSSFWorkbook(fis);
            if(workbook == null) count = 0;
            count = workbook.getSheetAt(0).getPhysicalNumberOfRows();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return count;
    }

    public static void createExcel(List objs,Class cls,File file) throws Exception{
        if (objs == null || objs.isEmpty()) {
            throw new IllegalArgumentException("the list is empty!");
        }
        boolean flag = parseExportClass(cls);
        if(!flag) throw new IllegalArgumentException("the class of object did not have @ExcelColumnName!");   //没有相关注解标明
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet();  //制作一个表格分页
        CellStyle headStyle = getHeadCellStyle(workbook);

        //生成Excel标题头
        Row headRow = sheet.createRow(0);
        headRow.setRowStyle(headStyle);
        Map<Integer, Integer> maxColumnWidth = new HashMap<>(); //记录每列的最大宽度
        Map<String,Integer> excelHead = columnName.get();
        excelHead.forEach((value,index)->{
            Cell cell = headRow.createCell(index);
            cell.setCellValue(value);
            maxColumnWidth.put(index, value.length());
        });

        //生成数据表格
        CellStyle cellStyle1 = getDataCellStyle(workbook);
        Map<String,Integer> excelColumn = columnName.get();

        for(int i = 0;i<objs.size();i++) {
            Row dataRow = sheet.createRow(i+1);
            Object obj = objs.get(i);
            objColumn.get().forEach((value,field)->{
                Integer index = excelColumn.get(value);
                dataRow.setRowStyle(cellStyle1);
                Cell cell = dataRow.createCell(index);
                String fieldValue = null;
                try {
                    String objFieldName = field.getName();
                    Method method = obj.getClass().getMethod("get" + objFieldName.substring(0,1).toUpperCase()+objFieldName.substring(1));
                    fieldValue = (String)method.invoke(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                cell.setCellValue(fieldValue);
                if(fieldValue == null) return;
                Integer width = maxColumnWidth.get(index);
                Integer nowWidth = fieldValue.length();
                if(width == null || width < nowWidth) {
                    maxColumnWidth.put(index, nowWidth);
                }
            });
        }

        //设置每一列的宽度
        maxColumnWidth.forEach((index,width)->{
            sheet.setColumnWidth(index,width<<9);
        });

        FileOutputStream fos = new FileOutputStream(file);
        try {
            workbook.write(fos);

        }catch (IOException e){
            e.printStackTrace();
        }finally {
            fos.close();

        }
    }

    private static boolean parseExportClass(Class cls){
        Field[] fields = cls.getDeclaredFields();
        Map<String, Field> objAnnotationMap = new HashMap<>();
        Map<String, Integer> excelColumnMap = new HashMap<>();
        for (Field field : fields) {
            ExcelColumnName excelColumnName = field.getAnnotation(ExcelColumnName.class);
            if (excelColumnName != null) {
                String value = excelColumnName.value();
                int index = excelColumnName.index();
                objAnnotationMap.put(value, field);
                excelColumnMap.put(value, index);
            }
        }
        if (!objAnnotationMap.isEmpty()){
            objColumn.set(objAnnotationMap); //将解析后的设置进变量存储
            columnName.set(excelColumnMap);
            return true;  //返回true表示该object里面含有相关注解
        }
        return false;
    }

    public static CellStyle getDataCellStyle(Workbook workbook){
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setIndention((short)2);
        cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
        Font cellFont = workbook.createFont();
        cellFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        cellStyle.setFont(cellFont);
        return cellStyle;
    }

    public static CellStyle getHeadCellStyle(Workbook workbook){
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        Font headerFont = workbook.createFont();
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headerStyle.setFont(headerFont);
        return headerStyle;
    }


    public static void main(String[] args) throws Exception{


//        TestObject tj = new TestObject();
//        tj.setName("身上看风景实力坑放暑假了");
//        tj.setSeriNum("2");
//
//        TestObject tj2 = new TestObject();
//        tj2.setName("乱写的测试数据设计费会计阿卡丽");
//        tj2.setSeriNum("3");
//
//        List<TestObject> objs = Lists.newArrayList(tj,tj2);
//        File file = new File("D:/exceltest.xls");
//
//        createExcel(objs,TestObject.class,file);

    }
}
