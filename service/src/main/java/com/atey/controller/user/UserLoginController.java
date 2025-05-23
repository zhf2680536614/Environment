package com.atey.controller.user;

import com.atey.dto.UserLoginDto;
import com.atey.result.Result;
import com.atey.service.UserLoginService;
import com.atey.vo.UserLoginVo;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@Api(tags = "用户登录相关接口")
@Slf4j
@RequiredArgsConstructor
public class UserLoginController {

    private final UserLoginService userLoginService;

    @PostMapping("/login")
    public Result<Object> login(@RequestBody UserLoginDto dto) {
        log.info("用户登录{}", dto.toString());
        UserLoginVo vo = userLoginService.login(dto);
        return Result.success(vo);
    }

}
