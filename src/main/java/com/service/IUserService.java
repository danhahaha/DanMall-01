package com.service;

import com.common.ServerResponse;
import com.pojo.User;
import net.sf.jsqlparser.schema.Server;

public interface IUserService {
    ServerResponse<User> login(String username, String password);

    ServerResponse<String> register(User user);

    ServerResponse checkAdminRole(User user);
}
