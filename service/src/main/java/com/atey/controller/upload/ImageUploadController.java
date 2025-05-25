package com.atey.controller.upload;

import com.atey.exception.BaseException;
import com.atey.result.Result;
import com.atey.utils.MinioFileStorageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/manage/upload")
@Slf4j
@AllArgsConstructor
@Api(tags = "文章上传相关接口")
public class ImageUploadController {

    private final MinioFileStorageUtil minioFileStorageUtil;

    @PostMapping("/image")
    @ApiOperation("上传图片")
    public Result<String> uploadImage(MultipartFile file) {
        log.info("上传图片");
        String url;
        String originalFilename = file.getOriginalFilename();
        try {
            InputStream inputStream = file.getInputStream();
            url = minioFileStorageUtil.uploadImage("", originalFilename, inputStream);
        } catch (IOException e) {
            throw new BaseException("文件上传异常");
        }
        return Result.success(url);
    }
}
