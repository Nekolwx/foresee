package com.foresee.ftcsp.order.auto.model;

import java.io.Serializable;
import java.util.Date;

public class OrdExcelTemp implements Serializable {
    /**
     * excel文件对应ID
     * 表 : t_ord_excel_temp
     * 对应字段 : excel_id
     */
    private Long excelId;

    /**
     * excel文件名
     * 表 : t_ord_excel_temp
     * 对应字段 : file_name
     */
    private String fileName;

    /**
     * 导入时间
     * 表 : t_ord_excel_temp
     * 对应字段 : import_time
     */
    private Date importTime;

    /**
     * 操作人
     * 表 : t_ord_excel_temp
     * 对应字段 : operator
     */
    private String operator;

    /**
     * 该表对应的总记录数(冗余字段)
     * 表 : t_ord_excel_temp
     * 对应字段 : all_count
     */
    private Integer allCount;

    /**
     * 该表对应的失败记录数(冗余字段)
     * 表 : t_ord_excel_temp
     * 对应字段 : fail_count
     */
    private Integer failCount;

    /**
     * 对应oss文件服务器地址
     * 表 : t_ord_excel_temp
     * 对应字段 : file_path
     */
    private String filePath;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_ord_excel_temp
     *
     * @MBG Generated Thu Nov 23 19:20:21 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * get method 
     *
     * @return t_ord_excel_temp.excel_id：excel文件对应ID
     */
    public Long getExcelId() {
        return excelId;
    }

    /**
     * set method 
     *
     * @param excelId  excel文件对应ID
     */
    public void setExcelId(Long excelId) {
        this.excelId = excelId;
    }

    /**
     * get method 
     *
     * @return t_ord_excel_temp.file_name：excel文件名
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * set method 
     *
     * @param fileName  excel文件名
     */
    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    /**
     * get method 
     *
     * @return t_ord_excel_temp.import_time：导入时间
     */
    public Date getImportTime() {
        return importTime;
    }

    /**
     * set method 
     *
     * @param importTime  导入时间
     */
    public void setImportTime(Date importTime) {
        this.importTime = importTime;
    }

    /**
     * get method 
     *
     * @return t_ord_excel_temp.operator：操作人
     */
    public String getOperator() {
        return operator;
    }

    /**
     * set method 
     *
     * @param operator  操作人
     */
    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    /**
     * get method 
     *
     * @return t_ord_excel_temp.all_count：该表对应的总记录数(冗余字段)
     */
    public Integer getAllCount() {
        return allCount;
    }

    /**
     * set method 
     *
     * @param allCount  该表对应的总记录数(冗余字段)
     */
    public void setAllCount(Integer allCount) {
        this.allCount = allCount;
    }

    /**
     * get method 
     *
     * @return t_ord_excel_temp.fail_count：该表对应的失败记录数(冗余字段)
     */
    public Integer getFailCount() {
        return failCount;
    }

    /**
     * set method 
     *
     * @param failCount  该表对应的失败记录数(冗余字段)
     */
    public void setFailCount(Integer failCount) {
        this.failCount = failCount;
    }

    /**
     * get method 
     *
     * @return t_ord_excel_temp.file_path：对应oss文件服务器地址
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * set method 
     *
     * @param filePath  对应oss文件服务器地址
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    /**
     *
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", excelId=").append(excelId);
        sb.append(", fileName=").append(fileName);
        sb.append(", importTime=").append(importTime);
        sb.append(", operator=").append(operator);
        sb.append(", allCount=").append(allCount);
        sb.append(", failCount=").append(failCount);
        sb.append(", filePath=").append(filePath);
        sb.append("]");
        return sb.toString();
    }
}