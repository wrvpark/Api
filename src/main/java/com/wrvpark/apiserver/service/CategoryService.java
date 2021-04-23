package com.wrvpark.apiserver.service;

import com.wrvpark.apiserver.dto.CategoryDTO;
import com.wrvpark.apiserver.dto.SubcategoryDTO;
import com.wrvpark.apiserver.util.ResultEntity;

import java.util.List;


/**
 * @author Isabel Ke
 * Original date:2020-02-07
 *
 * Description:category service interface
 */
public interface CategoryService {
    //get all categories
    ResultEntity<List<CategoryDTO>> getAllCategories(String message);
    //add a new sub-category
    ResultEntity<List<CategoryDTO>> addSubCategories(SubcategoryDTO subCategoryDTO);
    //delete a sub-category
    ResultEntity<List<CategoryDTO>> deleteSubCategoryById(String subId);
    //get sub-categories for a main category
    ResultEntity<List<SubcategoryDTO>> getSubCategoriesByPid(String pId);

    ResultEntity<List<SubcategoryDTO>> getSubCategoriesByParentName(String pName);
}
