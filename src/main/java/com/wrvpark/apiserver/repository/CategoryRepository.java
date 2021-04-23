package com.wrvpark.apiserver.repository;

import com.wrvpark.apiserver.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author Isabel Ke
 * @author Vahid Haghighat
 * Original date:2020-02-07
 *
 * Description:category repository that will interact with the database
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, String>, JpaSpecificationExecutor<Category> {
    Category findByName(String name);
}
