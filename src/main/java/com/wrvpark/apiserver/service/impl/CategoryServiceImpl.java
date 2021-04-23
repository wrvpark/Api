package com.wrvpark.apiserver.service.impl;

import com.wrvpark.apiserver.domain.Category;
import com.wrvpark.apiserver.domain.Subcategory;
import com.wrvpark.apiserver.dto.CategoryDTO;
import com.wrvpark.apiserver.dto.SubcategoryDTO;
import com.wrvpark.apiserver.repository.CategoryRepository;
import com.wrvpark.apiserver.repository.SubCategoryRepository;
import com.wrvpark.apiserver.service.CategoryService;
import com.wrvpark.apiserver.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


/**
 * @author Isabel Ke
 * @author Vahid Haghighat
 * Original date:2020-02-07
 *
 * Description:category service class that handles all the category logic
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SubCategoryRepository subCategoryRepository;

    /**
     * find all the categories
     *
     * @return a list of categories or "No categories"
     */
    @Override
    public ResultEntity<List<CategoryDTO>> getAllCategories(String message) {
        List<Category> categories = categoryRepository.findAll();

        //check if there is any categories
        if (categories.size() == 0 || categories == null) {
            return ResultEntity.successWithOutData("No Categories!");
        }
        List<CategoryDTO> categoryDTOS=new ArrayList<>();
        for (int i = 0; i < categories.size(); i++) {
            CategoryDTO categoryDTO=new CategoryDTO(categories.get(i));
            //set the sub-categories
            List<Subcategory> subcategories=categories.get(i).getSubcategoryList();
            List<SubcategoryDTO> subcategoryDTOS=new ArrayList<>();
            for (int j = 0; j < subcategories.size(); j++) {
                subcategoryDTOS.add(new SubcategoryDTO(subcategories.get(j)));
            }
            categoryDTO.setSubcategories(subcategoryDTOS);
            //add the category dto to the catetorydtos list
            categoryDTOS.add(categoryDTO);
        }

        return ResultEntity.successWithData(categoryDTOS, message);
    }

    /**
     * add a new sub-category to a main category
     *
     * @param subCategoryDTO
     * @return
     */
    @Override
    public ResultEntity<List<CategoryDTO>> addSubCategories(SubcategoryDTO subCategoryDTO) {
        String parentId = subCategoryDTO.getParentId();

        if (categoryRepository.findById(parentId).isEmpty())
            return ResultEntity.failed("This category does not exist");

        Category category = categoryRepository.findById(parentId).get();
        Subcategory subcategory = subCategoryRepository.findByName(subCategoryDTO.getName());

        if (category.getSubcategoryList().contains(subcategory))
            return ResultEntity.failed("This subcategory already exists");

        subCategoryRepository.save(subcategory);
        //get the newest categories
        return getAllCategories("The sub-category has been added!");
    }

    /**
     * delete a sub-category
     *
     * @param subId id of the sub-category that will be deleted
     * @return a list of categories after deleting successfully,
     * or an error message if fails.
     */
    @Override
    public ResultEntity<List<CategoryDTO>> deleteSubCategoryById(String subId) {
        System.out.println("inside delete");
        //find the subcategory
        Subcategory subcategory = subCategoryRepository.findById(subId).get();
        subcategory.setCategory(null);
        try {
            subCategoryRepository.delete(subcategory);
            return getAllCategories("Sub-category has been deleted");
        } catch (Exception e) {
            String error = e.getCause().getCause().getMessage();
            String message = "";
            if (error.contains("violates foreign key constraint")) {
                message = "This sub-category has been used by other items!";
            }
            return ResultEntity.failed(message);
        }

    }

    /**
     * get sub-categories for a main category
     *
     * @param pId main category id
     * @return a list of sub-categories of a main category
     */
    @Override
    public ResultEntity<List<SubcategoryDTO>> getSubCategoriesByPid(String pId) {
        if (categoryRepository.findById(pId).isPresent()) {
            List<Subcategory> subcategories = categoryRepository.findById(pId).get().getSubcategoryList();
            //check if there is any subcategories
            if (subcategories == null || subcategories.size() == 0) {
                return ResultEntity.successWithOutData("No Sub-Categories!");
            }
            //map the subcategories to dtos
            List<SubcategoryDTO> subCategoryDTOS = getSubcategoryDTOS(subcategories);
            return ResultEntity.successWithData(subCategoryDTOS, "Find sub-categories");
        }
        return ResultEntity.successWithOutData("Wrong Category ID");
    }

    /**
     * find sub-categories by the main category name
     * @param pName main category name
     * @return a list of sub-categories for this main category
     */
    @Override
    public ResultEntity<List<SubcategoryDTO>> getSubCategoriesByParentName(String pName) {
        if(pName.isEmpty())
        {
            return ResultEntity.failed("Main category name cannot be empty");
        }
        pName = pName.startsWith(":") ? pName.substring(1) : pName;
        Category category=categoryRepository.findByName(pName);
        if(category==null)
        {
            return ResultEntity.failed("This category does not exist");
        }
        List<Subcategory> subcategories =category.getSubcategoryList();
        //check if there is any subcategories
        if (subcategories.size() == 0 ) {
            return ResultEntity.successWithOutData("No Sub-Categories!");
        }
        List<SubcategoryDTO> subCategoryDTOS = getSubcategoryDTOS(subcategories);
        return ResultEntity.successWithData(subCategoryDTOS, "Find sub-categories");
    }

    /**
     * convert a list of Subcategory to a list of SubcategoryDTO
     * @param subcategories the original subcategories
     * @return the list of dtos
     */
    private List<SubcategoryDTO> getSubcategoryDTOS(List<Subcategory> subcategories) {
        //map the subcategories to dtos
        List<SubcategoryDTO> subCategoryDTOS = new ArrayList<>();
        for (int j = 0; j < subcategories.size(); j++) {
            Subcategory subCategory = subcategories.get(j);
            SubcategoryDTO subCategoryDTO = new SubcategoryDTO(subCategory.getId(),
                    subCategory.getCategory().getId(),
                    subCategory.getName(),
                    subCategory.getType());
            subCategoryDTOS.add(subCategoryDTO);

        }
        return subCategoryDTOS;
    }


}