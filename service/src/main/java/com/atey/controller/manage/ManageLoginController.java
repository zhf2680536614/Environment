package com.atey.controller.manage;

import com.atey.dto.UserLoginDto;
import com.atey.result.Result;
import com.atey.service.ManageLoginService;
import com.atey.vo.ManageLoginVo;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/manage")
@Api(tags = "管理员登录相关接口")
@Slf4j
@RequiredArgsConstructor
public class ManageLoginController {
    private final ManageLoginService manageLoginService;

    @PostMapping("/login")
    public Result<Object> login(@RequestBody UserLoginDto dto) {
        log.info("管理员登录{}", dto.toString());
        ManageLoginVo vo = manageLoginService.login(dto);
        return Result.success(vo);
    }
}
