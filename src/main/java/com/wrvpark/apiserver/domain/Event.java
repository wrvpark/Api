package com.wrvpark.apiserver.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Vahid Haghighat
 * @author Isabel Ke
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "RVPARK_EVENTS")
public class Event implements Serializable {
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid2")
    private String id;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "DESCRIPTION", columnDefinition = "TEXT")
    private String description;

    @Column(name="CREATE_TIME",nullable = false)
    private Date createTime;

    @Column(name = "START_TIME", nullable = false)
    private Date startTime;

    @Column(name = "END_TIME",nullable = false)
    private Date endTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOCATION_SUB_ID", referencedColumnName = "ID", nullable = false)
    private Subcategory locationSubcategory;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DESC_SUB_ID", referencedColumnName = "ID", nullable = false)
    private Subcategory descSubcategory;

    @ManyToOne()
    @JoinColumn(name = "CREATOR_ID", referencedColumnName = "ID",nullable = false)
    private User creator;

    @Column(name = "DETAILS", columnDefinition = "TEXT")
    private String details;

    /**
     * mark if this event is recurring
     */
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "RECURING_ID", referencedColumnName = "ID", nullable = true)
    private EventRecuring recurring;

   @Column(name = "file_name", nullable = true)
    private String fileName;

    @Column(name = "IS_LIMITED",columnDefinition = "boolean default false",nullable = false)
    private boolean isLimited=false;


    public Event(String id,
                 User creator,
                 String title,
                 String description,
                 String details,
                 Date startTime,
                 Date endTime,
                 Subcategory locationSubcategory,
                 Subcategory descSubcategory,
                 boolean limited)
    {
        this.id = id;
        this.creator = creator;
        this.title=title;
        this.description=description;
        this.createTime= new Date(System.currentTimeMillis());
        this.details=details;
        this.startTime=startTime;
        this.endTime=endTime;
        this.locationSubcategory = locationSubcategory;
        this.descSubcategory = descSubcategory;
        this.isLimited=limited;
    }
}