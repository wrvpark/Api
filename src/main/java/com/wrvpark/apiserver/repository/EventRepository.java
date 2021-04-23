package com.wrvpark.apiserver.repository;

import com.wrvpark.apiserver.domain.Event;
import com.wrvpark.apiserver.domain.Subcategory;
import com.wrvpark.apiserver.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.List;


/**
 * @author Isabel Ke
 * @author Vahid Haghighat
 * Original date:2020-02-07
 *
 * Description:event repository that will interact with the database
 */
@Repository
public interface EventRepository extends JpaRepository<Event,String>, JpaSpecificationExecutor<Event> {
    Event findByTitle(String title);
    List<Event> findByLocationSubcategoryAndDescSubcategory(Subcategory location, Subcategory description);
    List<Event> findByLocationSubcategory(Subcategory location);
    List<Event> findByDescSubcategory(Subcategory description);
    List<Event> findByCreator(User user);
}
