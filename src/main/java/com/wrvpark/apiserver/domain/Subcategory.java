package com.wrvpark.apiserver.domain;

import com.wrvpark.apiserver.dto.SubcategoryDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @author Vahid Haghighat
 * @author Isabel Ke
 */

@Entity
@Table(name = "RVPARK_SUBCATEGORIES")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Subcategory implements Serializable, Comparable<Subcategory> {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid2")
    @Column(name = "ID", length = 36)
    private String id;

    @Column(name = "NAME", unique = true, nullable = false)
    private String name;

    //type value can only be location or description.
    //the values will be used later
    @Column(name="TYPE", nullable = true, columnDefinition = "VARCHAR(50) CHECK (type IN ('location','description', ''))")
    private String type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CATEGORY_ID", referencedColumnName = "ID", nullable = false)
    private Category category;


    //a sub-category can have many parkdocuments
    @OneToMany(
            mappedBy = "subCategory",
            fetch = FetchType.LAZY
    )
    private List<ParkDocument> parkDocumentList;

    public Subcategory(String id) {
        this(id, "");
    }
    public Subcategory(String id, String name) {
        this.id=id;
        this.name=name;
    }

    public Subcategory(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    public Subcategory(String name, Category category, String type) {
        this.name = name;
        this.category = category;
        this.type = type;
    }

    public Subcategory(SubcategoryDTO subcategoryDTO) {
        this.type = subcategoryDTO.getType();
        this.name = subcategoryDTO.getName();
        this.category = new Category(subcategoryDTO.getParentId(), "");
    }

    @Override
    public int compareTo(Subcategory o) {
        if (o.getId().equals(id))
            return 0;
        return 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subcategory that = (Subcategory) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}