package com.wrvpark.apiserver.repository;

import com.wrvpark.apiserver.domain.Item;
import com.wrvpark.apiserver.domain.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.Collection;
import java.util.List;

/**
 * @author Chen Zhao
 * @author Vahid Haghighat
 * Original date:2020-02-18
 *
 * Description:Item repository that will interact with the database
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, String>, JpaSpecificationExecutor<Item> {
    List<Item> findAllByStatusAndSubcategoryIn(String status, Collection<Subcategory> subcategory);
    List<Item> findAllByStatusAndSubcategory(String status, Subcategory subcategory);
}

