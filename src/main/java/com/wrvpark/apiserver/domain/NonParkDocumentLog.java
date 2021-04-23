package com.wrvpark.apiserver.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Isabel Ke
 * @author Vahid Haghighat
 */

@Entity
@Table(name = "RVPARK_NONPARKDOCUMENTLOG")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NonParkDocumentLog implements Serializable {
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid2")
    private String id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MODIFIER_ID", referencedColumnName = "ID", nullable = false)
    private User modifier;

    @Column(name = "REASON", columnDefinition = "TEXT")
    private String reason;

    @Column(name = "ACTION",nullable = false,
            columnDefinition = "VARCHAR(100) CHECK (action IN ('CREATE','UPDATE','DELETE'))"
    )
    private String action;

    @Column(name = "create_time", nullable = false)
    private Date createTime;

    @Column(name = "DESCRIPTION", columnDefinition = "TEXT")
    private String description;

    @Column(name = "ITEM_ID")
    private String itemId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_ID", referencedColumnName = "ID", nullable = false)
    private Category category;

    public NonParkDocumentLog(User modifier, String reason, String description,
                              String delete, Date currentTime, String itemId,
                              Category category) {
        this.modifier=modifier;
        this.reason=reason;
        this.description=description;
        this.action=delete;
        this.itemId=itemId;
        this.createTime=currentTime;
        this.category=category;
    }

    public NonParkDocumentLog(String id,
                              String item_id,
                              String action,
                              Date create_time,
                              String description,
                              String reason) {
        this.id=id;
        this.itemId=item_id;
        this.action=action;
        this.createTime=create_time;
        this.description=description;
        this.reason=reason;
    }

    public NonParkDocumentLog(ParkDocument document, String action, String description, String reason) {
        this.itemId = document.getId();
        this.action = action;
        this.createTime = new Date();
        this.description = description;
        this.reason = reason;
        this.modifier = new User(document.getCreator().getId());
        this.category = document.getSubCategory().getCategory();
    }

    public NonParkDocumentLog(ParkDocument document, User modifier, String action, String description, String reason) {
        this.itemId = document.getId();
        this.action = action;
        this.createTime = new Date();
        this.description = description;
        this.reason = reason;
        this.modifier = modifier;
        this.category = document.getSubCategory().getCategory();
    }
}