package com.wrvpark.apiserver.domain;

import com.wrvpark.apiserver.dto.requests.NewForumPost;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Vahid Haghighat
 */

@Entity
@Table(name = "RVPARK_FORUM")
public class Forum implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid2")
    @Column(name = "ID")
    private String id;

    @Column(name = "ISCLOSED", nullable = false)
    private boolean isClosed;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "DETAILS", columnDefinition = "TEXT")
    private String details;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CREATOR_ID", referencedColumnName = "ID", nullable = false)
    private User creator;

    @Column(name = "CREATEDATE")
    private Date createDate;

    @Column(name = "FILE", columnDefinition = "TEXT")
    private String file;

    @ManyToMany(mappedBy = "subscriptions")
    private List<User> subscriber;

    @OneToMany(mappedBy = "forum",cascade = CascadeType.ALL)
    private List<Response> responses;

    public Forum()  { }

    public Forum(String id) {
        this.id = id;
    }

    public Forum(String title,
                 String details,
                 String type,
                 User creator,
                 Date createDate,
                 String file,
                 boolean isClosed) {
        this.isClosed = isClosed;
        this.title = title;
        this.type = type;
        this.details = details;
        this.creator = creator;
        this.createDate = createDate;
        this.file = file;
        this.subscriber = new ArrayList<>();
        this.responses = new ArrayList<>();
    }

    public Forum(NewForumPost post, String userId) {
        this.isClosed = false;
        this.title = (post.getTitle() == null || post.getTitle().isEmpty()) ? "" : post.getTitle();
        this.type = (post.getType() == null || post.getType().isEmpty()) ? "" : post.getType();
        this.details = (post.getDetails() == null || post.getDetails().isEmpty()) ? "" : post.getDetails();
        this.creator = new User(userId);
        this.createDate = new Date();
        this.file = post.getFilesString();
        this.subscriber = new ArrayList<>();
        this.responses = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) { this.type = type; }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public List<User> getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(List<User> subscriber) {
        this.subscriber = subscriber;
    }

    public List<Response> getResponses() {
        return responses;
    }

    public void setResponses(List<Response> responses) {
        this.responses = responses;
    }
}