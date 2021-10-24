package com.service;

import com.common.Const;
import com.common.ResponseCode;
import com.common.ServerResponse;
import com.dao.UserMapper;
import com.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse<User> login(String username, String password) {
        int count = userMapper.checkUserName(username);
        if (count == 0) {
            return ServerResponse.createByErrorMessage("用户不存在");
        }

        User user = userMapper.selectLogin(username, password);
        if (null == user) {
            return ServerResponse.createByErrorMessage("密码错误");
        }

        ServerResponse<User> response = ServerResponse.createBySuccess(user);
        user.setPassword(null);

        return response;
    }

    @Override
    public ServerResponse register(User user) {
        String username = user.getUsername();
        if (userMapper.checkUserName(username) != 0) {
            return ServerResponse.createByErrorMessage("用户名已存在");
        }

        user.setRole(Const.Role.ROLE_CUSTOMER);
        int result = userMapper.insert(user);
        if (0 == result) {
            return ServerResponse.createByErrorMessage("注册失败");
        } else {
            return ServerResponse.createByErrorMessage("注册成功");
        }
    }
}
