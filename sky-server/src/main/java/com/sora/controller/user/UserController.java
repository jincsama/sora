package com.sora.controller.user;

import com.sora.constant.JwtClaimsConstant;
import com.sora.dto.UserLoginDTO;
import com.sora.entity.User;
import com.sora.properties.JwtProperties;
import com.sora.result.Result;
import com.sora.service.UserService;
import com.sora.utils.JwtUtil;
import com.sora.vo.UserLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user/user")
@Slf4j
@Api(tags = "C端用户相关接口")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProperties jwtProperties;

    @PostMapping("/login")
    @ApiOperation("用户登录接口")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO loginDTO) {
        log.info("微信用户登录：{}", loginDTO.getCode());
        User user = userService.wxLogin(loginDTO);

        //生成令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);

        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId()).openid(user.getOpenid())
                .token(token).build();

        return Result.success(userLoginVO);
    }
}
