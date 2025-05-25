package com.atey.utils;

import ch.qos.logback.core.util.StringUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.atey.properties.MinioProperties;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class MinioFileStorageUtil {

    private final MinioClient minioClient;

    private final MinioProperties minioProperties;
    //分隔符
    private final static String separator = "/";

    public String buildFilePath(String dirPath, String filename) {
        StringBuilder stringBuilder = new StringBuilder(50);
        if (StrUtil.isNotBlank(dirPath)) {
            stringBuilder.append(dirPath).append(separator);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String todayDate = sdf.format(new Date());
        UUID uuid = UUID.randomUUID();
        stringBuilder.append(todayDate).append(separator).append(uuid).append(filename);
        return stringBuilder.toString();
    }

    public String uploadImage(String prefix, String filename, InputStream inputStream) {
        String filePath = buildFilePath(prefix, filename);
        try {
            PutObjectArgs putObject = PutObjectArgs.builder()
                    .object(filePath)
                    .contentType("image/jpg")
                    .bucket(minioProperties.getBucket()).stream(inputStream, inputStream.available(), -1)
                    .build();
            minioClient.putObject(putObject);
            return minioProperties.getReadPath() + separator + minioProperties.getBucket() +
                    separator +
                    filePath;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
