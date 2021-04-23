package com.wrvpark.apiserver.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

/**
 * @author Vahid Haghighat
 * @author Isabel Ke
 */

@Entity
@Table(name = "RVPARK_PICTURES")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Picture implements Serializable {


    @Id//mark this column is a primary key
    //primary generating strategy
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid2")
    @Column(name = "ID")
    private String id;
    //picture name
    @Column(name = "picture_name", nullable = false)
    private String pictureName;
    @Column(name = "upload_dir",nullable = false)
    private String uploadDir;

    public Picture(String pictureName, String uploadDir) {
        this.pictureName = pictureName;
        this.uploadDir = uploadDir;
    }

    public Picture(String pictureName) {
        this.pictureName = pictureName;
        this.uploadDir = pictureName;
    }
}