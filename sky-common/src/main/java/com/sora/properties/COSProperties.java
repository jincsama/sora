package com.sora.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sky.qcloud")
@Data
public class COSProperties {
    private String regions;
    private String secretId;
    private String secretKey;
    private String bucketName;
    private String localFilePath;
    private String filePath;
}
