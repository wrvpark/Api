package com.wrvpark.apiserver.repository;

import com.wrvpark.apiserver.domain.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Vahid Haghighat
 */
public interface ResponseRepository extends JpaRepository<Response, String>, JpaSpecificationExecutor<Response> {}