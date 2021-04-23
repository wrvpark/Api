package com.wrvpark.apiserver.controllers;


import com.wrvpark.apiserver.dto.CategoryDTO;
import com.wrvpark.apiserver.dto.SubcategoryDTO;
import com.wrvpark.apiserver.service.CategoryService;
import com.wrvpark.apiserver.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Isabel Ke
 * Original date:2020-02-07
 *
 * Description:category controller
 */

@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("/categories")
public class CategoryController
{

    @Autowired
    private CategoryService categoryService;

    /**
     * get all categories
     * @return a customized json data type
     */
    @GetMapping("")
    public ResultEntity<List<CategoryDTO>> getAllCategories()
    {
        return categoryService.getAllCategories(null);
    }

    /**
     * add a new sub-category to an existing main category
     * @param subCategoryDTO the new sub-category that will be added
     * @return a list of categories after add this sub-category
     */
    @PostMapping("/document")
    public ResultEntity<List<CategoryDTO>> addParkDocumentSubCategories(@RequestBody SubcategoryDTO subCategoryDTO)
    {
        return categoryService.addSubCategories(subCategoryDTO);
    }

    /**
     * add a new sub-category for non-park documents
     * @param subCategoryDTO the new sub-category information
     * @return a list of categories after addition, or an error message if it fails
     */
    @PostMapping("/other")
    public ResultEntity<List<CategoryDTO>> addSubCategories(@RequestBody SubcategoryDTO subCategoryDTO)
    {
        return categoryService.addSubCategories(subCategoryDTO);
    }
    /**
     * delete a sub-category by its id
     * @param subId id of the sub-category that will be deleted
     * @return  a list of categories if the delete is success, otherwise returns an error message
     */
    @DeleteMapping("/document/{subId}")
    public ResultEntity<List<CategoryDTO>> deleteParkDocumentSubCategoryById(@PathVariable String subId)
    {
        return  categoryService.deleteSubCategoryById(subId);
    }

    /**
     * delete a non-park document sub-category
     * @param subId the sub-category id that will be delted
     * @return  a list of categories if the delete is success, otherwise returns an error message
     */
    @DeleteMapping("/other/{subId}")
    public ResultEntity<List<CategoryDTO>> deleteNonParkDocumentSubCategoryById(@PathVariable String subId )
    {
        return  categoryService.deleteSubCategoryById(subId);
    }

    /**
     * seach sub-categories by the main category id
     * @param pId the main category id
     * @return a list of matched sub-categories, or empty data with message.
     */
    @GetMapping("/id/{pId}")
    public ResultEntity<List<SubcategoryDTO>> getSubCategoriesByPid(@PathVariable String pId)
    {
        return  categoryService.getSubCategoriesByPid(pId);
    }

    /**
     * search sub-categories by main category name
     * @param pName main category name
     * @return a list of matched sub-categories, or empty data with message.
     */
    @GetMapping("/name/{pName}")
    public ResultEntity<List<SubcategoryDTO>> getSubCategoriesByParentName(@PathVariable String pName)
    {
        return  categoryService.getSubCategoriesByParentName(pName);
    }
}
