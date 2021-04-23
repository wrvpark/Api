package com.wrvpark.apiserver.repository;

import com.wrvpark.apiserver.domain.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * @author Isabel Ke
 * Original date:2020-02-17
 *
 * Description:picture repository that will interact with the database
 */

@Repository
public interface PictureRepository extends JpaRepository<Picture,String> {
}
