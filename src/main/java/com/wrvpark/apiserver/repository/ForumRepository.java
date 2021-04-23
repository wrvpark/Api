package com.wrvpark.apiserver.repository;

import com.wrvpark.apiserver.domain.Forum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author Vahid Haghighat
 */
@Repository
public interface ForumRepository extends JpaRepository<Forum, String>, JpaSpecificationExecutor<Forum> {
    List<Forum> findForumByTitleInAndDetailsInAndCreateDateAfterAndCreateDateBefore(
            List<String> title,
            List<String> details,
            Date from,
            Date to);
}