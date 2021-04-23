package com.wrvpark.apiserver.repository;

import com.wrvpark.apiserver.domain.ParkDocument;
import com.wrvpark.apiserver.domain.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.List;


/**
 * @author Isabel Ke
 * @author Vahid Haghighat
 * Original date:2020-02-18
 *
 * Description:park document repository that will interact with the database
 */

@Repository
public interface ParkDocumentRepository extends JpaRepository<ParkDocument,String>, JpaSpecificationExecutor<ParkDocument> {
    List<ParkDocument> findAllByStatusOrderByCreateTimeDesc(String status);
    List<ParkDocument> findAllByStatusAndSubCategoryOrderByCreateTimeDesc(String status, Subcategory subcategory);
}