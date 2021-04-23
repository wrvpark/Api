package com.wrvpark.apiserver.domain;

import com.wrvpark.apiserver.dto.requests.PostResponseDTO;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * @author Vahid Haghighat
 */

@Entity
@Table(name = "RVPARK_RESPONSES")
public class Response implements Serializable {
    @Id
    @Column(name = "ID", length = 36)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid2")
    private String id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RESPONDER_ID", referencedColumnName = "ID", nullable = false)
    private User responder;

    @Column(name = "DATE")
    private Date date;

    @Column(name = "DETAILS", columnDefinition = "TEXT")
    private String details;

    @Column(name = "IMAGE", columnDefinition = "TEXT")
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FORUM_ID", referencedColumnName = "ID", nullable = false)
    private Forum forum;

    public Response()  {}

    public Response(User responder, Date date, String details, String image, Forum forum) {
        this.responder = responder;
        this.date = date;
        this.details = details;
        this.image = image;
        this.forum = forum;
    }

    public Response(PostResponseDTO responseDTO, String responderId, String forumId) {
        this.responder = new User(responderId);
        this.date = new Date();
        this.details = responseDTO.getDetails();
        this.image = responseDTO.getImage();
        this.forum = new Forum(forumId);
    }

    public String getId() {
        return id;
    }

    public User getResponder() {
        return responder;
    }

    public void setResponder(User responder) {
        this.responder = responder;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Forum getForum() {
        return forum;
    }

    public void setForum(Forum forum) {
        this.forum = forum;
    }
}