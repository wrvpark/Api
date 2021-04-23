package com.wrvpark.apiserver.domain;

import com.wrvpark.apiserver.dto.UserDTO;
import com.wrvpark.apiserver.dto.requests.authentication.RegistrationRequest;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Vahid Haghighat
 */

@Entity
@Table(name = "RVPARK_USER")
public class User implements Serializable {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "EMAIL")
    private String email;

    // "-" delimited series of integers
    @Column(name = "LOT_NO")
    private String lotNo;

    //JSON Object
    @Column(name = "ROLES")
    private String roles;

    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
    private List<Event> events;

    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
    private List<ParkDocument> parkDocuments;

    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
    private List<Forum> forums;

    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
    private List<Item> items;

    @OneToMany(mappedBy = "responder", fetch = FetchType.LAZY)
    private List<Response> responses;

    @ManyToMany
    @JoinTable(
            name = "RVPARK_SUBSCRIPTIONS",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "FORUM_ID")
    )
    private List<Forum> subscriptions;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "NOTIFICATION_ID", referencedColumnName = "ID", nullable = false)
    private Notification notification;

    @Column(name = "TOKEN")
    private String token;

    public User() {}

    public User(UserDTO userDTO) {
        this.id = userDTO.getId();
        this.firstName = userDTO.getFirstName();
        this.lastName = userDTO.getLastName();
    }

    public User(String id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public User(RegistrationRequest request, String id) {
        this.id = id;
        this.firstName = request.getFirstName();
        this.lastName = request.getLastName();
        this.email = request.getEmail();
        this.lotNo = Integer.toString(request.getLotNo());
        Role role = new Role(request.isOwner(),
                             request.isRenter(),
                             false,//request.isBoardMember(),
                             false,//request.isParkManagement(),
                             false,//request.isAdmin(),
                            true,
                            true);
        this.roles = role.toString();
    }

    public User(String id) {
        this.id = id;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setRoles(String roles) { this.roles = roles; }

    public void setRoles(Role roles) { this.roles = roles.toString(); }

    public Role getRoles() {
        return new Role(roles);
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Integer> getLotNo() {
        if (lotNo == null || lotNo.isEmpty())
            return new ArrayList<>();
        String[] lots = lotNo.split("-");
        List<Integer> output = new ArrayList<>();
        for (String lot : lots)
            output.add(Integer.parseInt(lot));
        return output;
    }

    public void setLotNo(List<Integer> lotNo) {
        StringBuilder builder = new StringBuilder();
        Iterator<Integer> iterator = lotNo.iterator();
        while (iterator.hasNext()) {
            builder.append(iterator.next());
            if (iterator.hasNext())
                builder.append("-");
        }
        this.lotNo = builder.toString();
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<ParkDocument> getParkDocuments() {
        return parkDocuments;
    }

    public void setParkDocuments(List<ParkDocument> parkDocuments) {
        this.parkDocuments = parkDocuments;
    }

    public List<Forum> getForums() {
        return forums;
    }

    public void setForums(List<Forum> forums) {
        this.forums = forums;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<Response> getResponses() {
        return responses;
    }

    public void setResponses(List<Response> responses) {
        this.responses = responses;
    }

    public List<Forum> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Forum> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public Notification getNotification() {
        return notification;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return id;
    }
}