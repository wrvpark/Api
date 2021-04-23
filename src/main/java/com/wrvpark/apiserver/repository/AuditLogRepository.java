package com.wrvpark.apiserver.repository;

import com.wrvpark.apiserver.domain.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author Isabel Ke
 * Original date:2020-02-07
 *
 * Description:audit log repository that will interact with the database
 */
@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, String>,
        JpaSpecificationExecutor<AuditLog> {
}
