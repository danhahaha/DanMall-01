package com.service;

import com.common.ServerResponse;
import com.pojo.User;

public interface IUserService {
    ServerResponse<User> login(String username, String password);

    ServerResponse<String> register(User user);
}
