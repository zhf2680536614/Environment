package com.atey.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Component
@ConfigurationProperties(prefix = "atey.minio")
public class MinioProperties implements Serializable {
    private String accessKey;
    private String secretKey;
    private String bucket;
    private String endPoint;
    private String readPath;
}
