package com.foresee.ftcsp.order.manual.restdto;

import org.hibernate.validator.constraints.NotBlank;

/**
 * <pre>
 * 文件key和文件名入参dto
 * </pre>
 *
 * @author liweixin@foresee.com.cn
 * @version 1.00.00
 *          <p>
 *          <pre>
 *          修改记录
 *          修改后版本:     修改人：  修改日期:     修改内容:
 *              </pre>
 * @date 2017/11/22
 */
public class ImportBatchDTO {

    @NotBlank(message = "文件路径名不能为空")
    private String filePath;

    @NotBlank(message = "文件名不能为空")
    private String fileName;

    @NotBlank(message = "操作人不能为空")
    private String userId;

    @NotBlank(message = "任务ID不能为空")
    private String taskId;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public String toString() {
        return "ImportBatchDTO{" +
                "filePath='" + filePath + '\'' +
                ", fileName='" + fileName + '\'' +
                ", userId='" + userId + '\'' +
                ", taskId='" + taskId + '\'' +
                '}';
    }
}
