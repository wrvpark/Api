package com.wrvpark.apiserver.configuration;

/**
 * @author Vahid Haghighat
 * Description: Static class to hold Authentication to Keycloak server's credentials.
 */

public final class AuthCredentials {
    public static final String REALM_WELL_KNOWN_ENDPOINT = "http://auth:8080/auth/realms/rvpark/.well-known/openid-configuration";
    public static final String TOKEN_ENDPOINT = "http://auth:8080/auth/realms/rvpark/protocol/openid-connect/token";
    public static final String USER_LOGIN_CONTENT_TYPE = "application/x-www-form-urlencoded";
    public static final String USER_LOGIN_GRANT_TYPE = "password";
    public static final String USER_REFRESH_GRANT_TYPE = "refresh_token";
    public static final String USER_LOGIN_CLIENT_ID = "users";
    public static final String USER_LOGIN_CLIENT_SECRET = "**********";
    public static final String REGISTRATION_GRANT_TYPE = "client_credentials";
    public static final String REGISTRATION_CLIENT_ID = "admin-cli";
    public static final String REGISTRATION_CLIENT_SECRET = "3d5e00aa-9e17-4943-aa9c-a633efd74d48";
}
