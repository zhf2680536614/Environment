package com.atey.controller.user;

import com.atey.result.Result;
import com.atey.utils.CreateCaptchaUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/user")
@Slf4j
@Api(tags = "用户相关接口/验证码接口")
@RequiredArgsConstructor
public class CaptchaController {

    @GetMapping("/captcha")
    @ApiOperation("验证码")
    public void getCaptcha(@RequestParam String key, HttpServletResponse response) throws IOException {
        log.info("生成验证码{}", key);
        CreateCaptchaUtil.createCaptcha(key, response);
    }

    @GetMapping("/captcha/validator")
    @ApiOperation("判断验证码是否输入正确")
    public Result<Boolean> validator(@RequestParam String key, @RequestParam String captcha) {
        log.info("验证验证码{}{}", key, captcha);
        return Result.success(CreateCaptchaUtil.validator(key, captcha));
    }
}
