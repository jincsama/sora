package com.sora.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class COSUtil {
    private String regions;
    private String secretId;
    private String secretKey;
    private String bucketName;
    private String filePath;

    public String upload(File file,String objectName) {
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        Region region = new Region(regions);
        com.qcloud.cos.ClientConfig clientConfig = new ClientConfig(region);
        clientConfig.setHttpProtocol(HttpProtocol.https);

        //  生成 cos 客户端
        COSClient cosClient = new COSClient(cred, clientConfig);

        // 指定要上传的文件
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,filePath + "/" + objectName, file);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);

        StringBuilder stringBuilder = new StringBuilder("https://");
        stringBuilder
                .append(bucketName)
                .append(".cos.")
                .append(regions)
                .append(".myqcloud.com/")
                .append(filePath)
                .append("%2F")
                .append(objectName);

        log.info("文件上传到:{}", stringBuilder.toString());
        return stringBuilder.toString();
    }
}
