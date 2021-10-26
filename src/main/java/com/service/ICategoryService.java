package com.service;

import com.common.ServerResponse;

public interface ICategoryService {
    ServerResponse addCategory(String categoryName, Integer parentId);

    ServerResponse updateCategoryName(String categoryName, Integer id);

    ServerResponse getChildrenCategoryById(Integer id);

    ServerResponse getAllId(Integer id);
}
