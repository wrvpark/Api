package com.wrvpark.apiserver.domain;

import com.wrvpark.apiserver.dto.requests.CreateParkDocumentDTO;
import com.wrvpark.apiserver.dto.requests.UpdateParkDocumentDTO;
import com.wrvpark.apiserver.util.TimeUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Vahid Haghighat
 * @author by Isabel Ke
 */

@Entity
@Table(name = "RVPARK_PARKDOCUMENTS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkDocument implements Serializable {
    @Id
    @Column(name = "ID", length = 36)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid2")
    private String id;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_id", referencedColumnName = "ID", nullable = false)
    private Subcategory subCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID", nullable = false)
    private User creator;

    @Column(name = "CREATE_TIME", nullable = false)
    private Date createTime;

    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    @Column(name = "DELETE_TIME")
    private Date deleteTime;

    //expiryTime should be 3 months later than the deleteTime
    @Column(name = "EXPIRY_TIME")
    private Date expiryTime;

    @Column(name = "DESCRIPTION", columnDefinition = "TEXT")
    private String description;

    @Column(name = "FILE", columnDefinition = "TEXT")
    private String fileName;

    @Column(name = "upload_dir")
    private String uploadDir;

    //status should only be CREATED,DELETED.
    @Column(name = "STATUS", columnDefinition = "VARCHAR(60) CHECK (status IN ('CREATED','DELETED'))")
    private String status;

    //one park document might have many audit logs
    @OneToMany(mappedBy = "parkDocument",cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<AuditLog> auditLog;


    //customised constructor
    public ParkDocument(String title, Subcategory subCategory, User creator, Date createTime, String description, String fileName, String uploadDir, String status) {
        this.title = title;
        this.subCategory = subCategory;
        this.creator = creator;
        this.createTime = createTime;
        this.updateTime = createTime;
        this.description = description;
        this.fileName = fileName;
        this.uploadDir = uploadDir;
        this.status = status;
    }

    public ParkDocument(CreateParkDocumentDTO document, String userId) {
        this.title = document.getTitle();
        this.subCategory = new Subcategory(document.getSubcategoryId());
        this.description = document.getDescription();
        this.fileName = document.getFileNameString();
        this.creator = new User(userId);
        this.createTime = new Date();
        this.updateTime = new Date();
        this.expiryTime = TimeUtil.getExpiryDate(this.createTime, 90);
        this.status = "CREATED";
    }

    public ParkDocument(UpdateParkDocumentDTO document, String documentId) {
        this.id = documentId;
        this.title = document.getTitle();
        this.subCategory = new Subcategory(document.getSubcategoryId());
        this.description = document.getDescription();
        this.fileName = document.getFileNameString();
        this.updateTime = new Date();
        this.status = "CREATED";
    }
}