package com.wrvpark.apiserver.repository;

import com.wrvpark.apiserver.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * @author Vahid Haghighat
 */
@Repository
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
    User findByFirstName(String name);
    User findByEmail(String email);
    User findByToken(String token);
    List<User> findAllByIdIn(Collection<String> id);
}
