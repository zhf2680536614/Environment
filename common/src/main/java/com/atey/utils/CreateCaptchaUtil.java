package com.atey.utils;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 图形验证码相关工具类
 */
@Component
public class CreateCaptchaUtil {

    //创建Map集合用来存储生成的验证码
    public static Map<String, String> CAPTCHA_MAP = new HashMap<>();

    //生成验证码
    public static void createCaptcha(String key, HttpServletResponse response) throws IOException {
        //定义图形验证码的长和宽
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(100, 50);
        String lowerCase = lineCaptcha.getCode().toLowerCase();
        CAPTCHA_MAP.put(key, lowerCase);
        lineCaptcha.write(response.getOutputStream());
        response.getOutputStream().close();
    }

    //验证验证码是否正确
    public static Boolean validator(String key, String captcha) {
        if (captcha.toLowerCase().equals(CAPTCHA_MAP.get(key))) {
            CAPTCHA_MAP.remove(key);
            return true;
        }
        CAPTCHA_MAP.remove(key);
        return false;
    }
}
