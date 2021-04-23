package com.wrvpark.apiserver.dto;

import com.wrvpark.apiserver.domain.User;
import java.util.List;

/**
 * @author Vahid Haghighat
 */
public class FullUserInformationDTO
{
    private final String id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final List<Integer> lotNo;
    private boolean isOwner;
    private boolean isRenter;
    private boolean isBoardMember;
    private boolean isParkManagement;
    private boolean isAdmin;
    private boolean isVisitor;
    private boolean isUnapproved;

    public FullUserInformationDTO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.lotNo = user.getLotNo();
        this.isAdmin = user.getRoles().isAdmin();
        this.isBoardMember = user.getRoles().isBoardMember();
        this.isOwner = user.getRoles().isOwner();
        this.isRenter = user.getRoles().isRenter();
        this.isParkManagement = user.getRoles().isParkManagement();
        this.isVisitor = user.getRoles().isVisitor();
        this.isUnapproved = user.getRoles().isUnapproved();
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public List<Integer> getLotNo() {
        return lotNo;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public boolean isRenter() {
        return isRenter;
    }

    public boolean isBoardMember() {
        return isBoardMember;
    }

    public boolean isParkManagement() {
        return isParkManagement;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public boolean isVisitor() {
        return isVisitor;
    }

    public boolean isUnapproved() {
        return isUnapproved;
    }

    public void setOwner(boolean owner) {
        isOwner = owner;
    }

    public void setRenter(boolean renter) {
        isRenter = renter;
    }

    public void setBoardMember(boolean boardMember) {
        isBoardMember = boardMember;
    }

    public void setParkManagement(boolean parkManagement) {
        isParkManagement = parkManagement;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public void setVisitor(boolean visitor) {
        isVisitor = visitor;
    }

    public void setUnapproved(boolean unapproved) {
        isUnapproved = unapproved;
    }
}
