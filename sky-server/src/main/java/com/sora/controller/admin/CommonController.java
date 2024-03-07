package com.sora.controller.admin;

import com.sora.constant.MessageConstant;
import com.sora.result.Result;
import com.sora.utils.COSUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/admin/common")
@ApiOperation("通用接口")
public class CommonController {

    @Autowired
    private COSUtil cosUtil;

    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public Result<String> upload(MultipartFile file){
        log.info("上传文件:{}", file);
        String originalFilename = file.getOriginalFilename();
        File fileJ = new File(originalFilename);
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String objectName = UUID.randomUUID().toString() + extension;

        // 方法1: 使用 FileCopyUtils.copy 方法
        try {
            FileCopyUtils.copy(file.getBytes(), fileJ);
            return Result.success(cosUtil.upload(fileJ, objectName));

        } catch (IOException e) {
            log.error("文件上传失败:{}", e);
        }

        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}
