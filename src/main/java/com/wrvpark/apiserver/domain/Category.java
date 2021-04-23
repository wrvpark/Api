package com.wrvpark.apiserver.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author Vahid Haghighat
 * @author Isabel Ke
 * Category entity
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "RVPARK_CATEGORIES")
public class Category implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid2")
    @Column(name = "ID", length = 36)
    private String id;

    /*
    this category name should be unique,and should not be updated.
    */
    @Column(name = "NAME",unique = true,nullable = false,updatable = false)
    //columnDefinition = "VARCHAR(100) CHECK (NAME IN ('Park Document', 'Event', 'For Sale or Rent','Services','Lost & Found')"
    private String name;

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    private List<Subcategory> subcategoryList;

    public Category(String name) {
        this.name = name;
    }

    public Category(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addSubcategory(Subcategory subcategory) {
        subcategoryList.add(subcategory);
        subcategory.setCategory(this);
    }
}