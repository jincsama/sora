package com.sora.config;

import com.sora.properties.COSProperties;
import com.sora.utils.COSUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class COSConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public COSUtil cosUtil(COSProperties cosProperties){
        log.info("创建文件上传工具类:{}",cosProperties);
        return new COSUtil(cosProperties.getRegions(), cosProperties.getSecretId(),
                cosProperties.getSecretKey(), cosProperties.getBucketName(), cosProperties.getFilePath());
    }
}
