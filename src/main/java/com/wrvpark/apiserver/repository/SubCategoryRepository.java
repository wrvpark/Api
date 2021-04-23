package com.wrvpark.apiserver.repository;

import com.wrvpark.apiserver.domain.Category;
import com.wrvpark.apiserver.domain.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;



/**
 * @author Isabel Ke
 * Original date:2020-02-07
 *
 * Description:sub-category repository that will interact with the database
 */
public interface SubCategoryRepository extends JpaRepository<Subcategory,String>, JpaSpecificationExecutor<Subcategory> {

    Subcategory findByName(String name);
    Subcategory findByNameAndType(String name, String type);
    Subcategory findByNameAndTypeIsNullAndCategory(String name, Category category);
}
