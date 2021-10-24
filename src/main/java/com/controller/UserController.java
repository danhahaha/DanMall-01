package com.controller;

import com.common.Const;
import com.common.ResponseCode;
import com.common.ServerResponse;
import com.pojo.User;
import com.service.IUserService;
import net.sf.jsqlparser.schema.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService iUserService;

    @RequestMapping("/login.do")
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session) {
        ServerResponse<User> response = iUserService.login(username, password);
        if (ResponseCode.SUCCESS.equals(response.getStatus())) {
            session.setAttribute(Const.CURRENT_USER, response.getData());
        }
        return response;
    }

    @RequestMapping("/logout.do")
    @ResponseBody
    public ServerResponse<String> logout(HttpSession session) {
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }

    @RequestMapping("/register.do")
    @ResponseBody
    public ServerResponse register(User user) {
        return iUserService.register(user);
    }

    // TODO check_valid
    // TODO find_back_count
    // TODO login_by_email

    @RequestMapping("/getinfo.do")
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (null == user) {
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return ServerResponse.createBySuccess(user);
    }
}
