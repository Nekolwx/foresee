package com.foresee.ftcsp.order.service.config;

import com.foresee.ftcsp.common.core.dict.annotation.FtcspDictConfig;
import com.foresee.ftcsp.common.core.dict.annotation.FtcspDictValue;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * TODO
 * </pre>
 *
 * @author liweixin@foresee.com.cn
 * @version 1.00.00
 *          <p>
 *          <pre>
 *          修改记录
 *          修改后版本:     修改人：  修改日期:     修改内容:
 *              </pre>
 * @date 2017/11/30
 */
@Component
@FtcspDictConfig(dictCode = "file_upload_config",autowire = false)
public class FileUpLoadConfig {

    @FtcspDictValue(value = "uploadIpPort")
    private String uploadIpPort;

    public String getUploadIpPort() {
        return uploadIpPort;
    }

    public void setUploadIpPort(String uploadIpPort) {
        this.uploadIpPort = uploadIpPort;
    }
}
