package com.sora.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sora.constant.MessageConstant;
import com.sora.dto.UserLoginDTO;
import com.sora.entity.User;
import com.sora.exception.LoginFailedException;
import com.sora.mapper.UserMapper;
import com.sora.properties.WeChatProperties;
import com.sora.service.UserService;
import com.sora.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    public static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";
    public static final String GRANT_TYPE = "authorization_code";
    @Autowired
    private WeChatProperties weChatProperties;
    @Autowired
    private UserMapper userMapper;

    /**
     * 用户登录
     *
     * @param loginDTO
     * @return
     */
    public User wxLogin(UserLoginDTO loginDTO) {
        //获取openid
        String openid = getOpenid(loginDTO);

        //openid处理,新用户自动注册
        if (openid == null) {
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }
        User user = userMapper.getByOpenId(openid);
        if (user == null) {
            user = User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now()).build();
            userMapper.insert(user);
        }


        return user;
    }

    private String getOpenid(UserLoginDTO loginDTO) {
        HashMap<String, String> map = new HashMap<>();
        map.put("appid", weChatProperties.getAppid());
        map.put("secret", weChatProperties.getSecret());
        map.put("js_code", loginDTO.getCode());
        map.put("grant_type", GRANT_TYPE);
        String json = HttpClientUtil.doGet(WX_LOGIN, map);
        JSONObject jsonObject = JSON.parseObject(json);
        String openid = jsonObject.getString("openid");
        return openid;
    }
}
