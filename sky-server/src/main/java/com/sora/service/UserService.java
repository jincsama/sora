package com.sora.service;

import com.sora.dto.UserLoginDTO;
import com.sora.entity.User;

public interface UserService {
    User wxLogin(UserLoginDTO loginDTO);

}
