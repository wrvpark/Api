package com.wrvpark.apiserver.dto.requests.authentication;

import java.io.Serializable;

/**
 * @author Vahid Haghighat
 */
public class LoginRequest implements Serializable {
    private String username;
    private String password;
    public LoginRequest() {}
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() { return password; }
    public void setPassword(String password) {
        this.password = password;
    }
}
