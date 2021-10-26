package com.service;

import com.common.ServerResponse;
import com.controller.backend.CategoryManageController;
import com.dao.CategoryMapper;
import com.github.pagehelper.StringUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.pojo.Category;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CategoryServiceImpl implements ICategoryService{

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public ServerResponse addCategory(String categoryName, Integer parentId) {
        if (parentId == null || StringUtils.isBlank(categoryName)) {
            return ServerResponse.createByErrorMessage("信息错误");
        }

        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        category.setStatus(true);

        int row = categoryMapper.insert(category);
        if (row > 0) {
            return ServerResponse.createBySuccessMessage("添加成功");
        }
        return ServerResponse.createByErrorMessage("添加失败");
    }

    @Override
    public ServerResponse updateCategoryName(String categoryName, Integer id) {
        if (StringUtils.isBlank(categoryName)) {
            return ServerResponse.createByErrorMessage("信息错误");
        }

        Category category = new Category();
        category.setStatus(true);
        category.setName(categoryName);
        category.setId(id);

        int row = categoryMapper.updateByPrimaryKeySelective(category);
        if (row > 0) {
            return ServerResponse.createBySuccessMessage("更新成功");
        }
        return ServerResponse.createByErrorMessage("更新失败");
    }

    @Override
    public ServerResponse<List<Category>> getChildrenCategoryById(Integer id) {
        if (id == null) {
            return ServerResponse.createByErrorMessage("信息错误");
        }

        List<Category> list = categoryMapper.selectChildrenById(id);
        return ServerResponse.createBySuccess(list);
    }

    @Override
    public ServerResponse<List<Integer>> getAllId(Integer id) {
        Set<Integer> set = Sets.newHashSet();
        findAllId(set, id);

        List list = Lists.newArrayList();
        for (Integer it : set) {
            list.add(it);
        }

        return ServerResponse.createBySuccess(list);
    }

    private void findAllId(Set<Integer> set, Integer id) {
        Category category = categoryMapper.selectByPrimaryKey(id);
        if (id == null) return;

        set.add(id);
        List<Category> list = categoryMapper.selectChildrenById(id);
        for (Category it : list) {
            findAllId(set, it.getId());
        }
    }
}
