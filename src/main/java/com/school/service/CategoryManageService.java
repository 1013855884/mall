package com.school.service;

import com.school.common.ServerResponse;
import com.school.pojo.Category;

import java.util.List;

public interface CategoryManageService {

    ServerResponse addCategory(String categoryName, Integer parentId);

    ServerResponse updateCategoryName(Integer categoryId,String categoryName);

    ServerResponse selectCategoryAndChildrenById(Integer categoryId);

    ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId);
}
