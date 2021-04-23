package com.wrvpark.apiserver.dto;

import com.wrvpark.apiserver.domain.Category;
import lombok.*;

import java.io.Serializable;
import java.util.List;


/**
 * @author Isabel Ke
 * Original date:2020-02-07
 *
 * Description:customize the category that will be returned to the client-side
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryDTO implements Serializable {
    private String id;
    private String name;
    private String parentId="0";//set a default value
    private List<SubcategoryDTO> subcategories;

    public CategoryDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }
}
