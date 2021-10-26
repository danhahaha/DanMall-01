package com.controller.backend;

import com.common.Const;
import com.common.ServerResponse;
import com.pojo.Category;
import com.pojo.User;
import com.service.ICategoryService;
import com.service.IUserService;
import net.sf.jsqlparser.schema.Server;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/manage/category")
public class CategoryManageController {

    @Autowired
    private ICategoryService iCategoryService;

    @Autowired
    private IUserService iUserService;

    @RequestMapping("/add_category.do")
    @ResponseBody
    public ServerResponse addCategory(HttpSession session, String categoryName, @RequestParam("parentId") Integer parentId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("未登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iCategoryService.addCategory(categoryName, parentId);
        }
        return ServerResponse.createByErrorMessage("不是管理员");
    }

    @RequestMapping("/update_category.do")
    @ResponseBody
    public ServerResponse setCategoryName(HttpSession session, String categoryName, Integer id) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("未登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iCategoryService.updateCategoryName(categoryName, id);
        }
        return ServerResponse.createByErrorMessage("不是管理员");
    }

    @RequestMapping("/get_childrenCategory.do")
    @ResponseBody
    public ServerResponse getChildrenCategoryById(HttpSession session, Integer id) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("未登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iCategoryService.getChildrenCategoryById(id);
        }
        return ServerResponse.createByErrorMessage("不是管理员");
    }

    @RequestMapping("/get_all_id.do")
    @ResponseBody
    public ServerResponse getAllId(HttpSession session, Integer id) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("未登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iCategoryService.getAllId(id);
        }
        return ServerResponse.createByErrorMessage("不是管理员");
    }
}
