package com.wrvpark.apiserver.dto;

import com.wrvpark.apiserver.domain.Subcategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * @author Isabel Ke
 * @author Vahid Haghighat
 * Original date:2020-02-07
 *
 * Description:customize the sub-category that will be returned to the client-side
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SubcategoryDTO {
    private String id;
    //set a default value
    private String parentId="0";
    private String name;
    private String type;

    public SubcategoryDTO(String id, String name) {
        this.id=id;
        this.name=name;
    }

    public SubcategoryDTO(Subcategory subcategory) {
        this.id = subcategory.getId();
        this.parentId = subcategory.getCategory().getId();
        this.name = subcategory.getName();
        this.type = subcategory.getType();
    }
}
