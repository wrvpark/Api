package com.wrvpark.apiserver.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 *  @author Vahid Haghighat
 *  @author Isabel Ke
 */

@Entity
@Table(name = "RVPARK_AUDITLOGS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog implements Serializable {
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid2")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MODIFIER_ID", referencedColumnName = "ID", nullable = false)
    private User modifier;

    @Column(name = "REASON", columnDefinition = "TEXT")
    private String reason;

    @Column(name = "ACTION",nullable = false,
            columnDefinition = "VARCHAR(100) CHECK (action IN ('CREATE','UPDATE','DELETE'))")
    private String action;

    @Column(name = "DATE", nullable = false)
    private Date date;

    @Column(name = "DESCRIPTION", columnDefinition = "TEXT")
    private String description;

    @ManyToOne()
    @JoinColumn(name = "PARKDOCUMENT_ID", referencedColumnName = "ID", nullable = false)
    private ParkDocument parkDocument;

}