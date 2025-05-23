package com.atey.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "atey.jwt")
public class JwtProperties {
    //用户jwt相关配置
    private String userSecretKey;
    private String userTokenName;
    private long userTtl;

    //管理员jwt相关配置
    private String manageSecretKey;
    private String manageTokenName;
    private long manageTtl;
}

