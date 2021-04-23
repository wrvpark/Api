package com.wrvpark.apiserver.repository;

import com.wrvpark.apiserver.domain.NonParkDocumentLog;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * @author Isabel Ke
 * Original date:2020-03-28
 *
 * Description:this log repository records all deletions for the role admin
 */
public interface NonParkDocumentLogRepository extends JpaRepository<NonParkDocumentLog,String> {

}
