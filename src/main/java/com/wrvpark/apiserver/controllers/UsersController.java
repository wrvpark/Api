package com.wrvpark.apiserver.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wrvpark.apiserver.configuration.AuthCredentials;
import com.wrvpark.apiserver.dto.requests.RefreshRequest;
import com.wrvpark.apiserver.dto.requests.authentication.*;
import com.wrvpark.apiserver.service.UserService;
import com.wrvpark.apiserver.util.ResultEntity;
import com.wrvpark.apiserver.util.SecurityUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import static org.keycloak.admin.client.CreatedResponseUtil.getCreatedId;

/**
 * @author Vahid Haghighat
 * Description: Handles the /user endpoint requests.
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/user")
public class UsersController {

    @Autowired
    private UserService userService;

    /**
     * Health check endpoint of the API
     * @param authentication The authentication information of the user.
     * @return True if the user checking the endpoint is admin, false otherwise.
     */
    @GetMapping(value = "/status/check")
    @ResponseBody
    public String status(Authentication authentication) {
        System.out.println(SecurityUtil.hasRole(authentication, "admin"));
        return "Working";
    }

    /**
     * Registers a new user to the website.
     * @param input The registration request.
     * @return The ID of the newly registered user.
     */
    @PostMapping(value = "/register")
    @ResponseBody
    public ResponseEntity<ResultEntity<String>> register(@RequestBody RegistrationRequest input) {
        try {
            // Checks for not empty credentials.
            if (input.getEmail().isEmpty() || input.getFirstName().isEmpty() || input.getLastName().isEmpty() ||
                !input.getPassword().equals(input.getConfirmPassword()))
                return new ResponseEntity<>(ResultEntity.failed("Invalid Form Submission!"), HttpStatus.BAD_REQUEST);
            Keycloak keycloak = getKeycloak();
            if (keycloak != null) {
                CredentialRepresentation credential = new CredentialRepresentation();
                credential.setType(CredentialRepresentation.PASSWORD);
                credential.setValue(input.getPassword());
                credential.setTemporary(false);
                UserRepresentation user = new UserRepresentation();
                user.setUsername(input.getEmail());
                user.setEmail(input.getEmail());
                user.setFirstName(input.getFirstName());
                user.setLastName(input.getLastName());
                user.setCredentials(Arrays.asList(credential));
                user.setEnabled(true);
                Response response = keycloak.realm("rvpark").users().create(user);
                if (response.getStatus() == 201) {
                    // Adding roles to the created user on Keycloak.
                    String userID = getCreatedId(response);
                    List<RoleRepresentation> serverRoles = keycloak
                            .realm("rvpark")
                            .roles()
                            .list();
                    List<String> searchableRoles = serverRoles
                            .stream()
                            .map(RoleRepresentation::getName)
                            .collect(Collectors.toList());
                    List<RoleRepresentation> roles = new ArrayList<>();
                    roles.add(serverRoles.get(searchableRoles.indexOf("visitor")));
                    roles.add(serverRoles.get(searchableRoles.indexOf("unapproved")));
                    if (input.isOwner()) roles.add(serverRoles.get(searchableRoles.indexOf("owner")));
                    if (input.isRenter()) roles.add(serverRoles.get(searchableRoles.indexOf("renter")));
//                    if (input.isBoardMember()) roles.add(serverRoles.get(searchableRoles.indexOf("board")));
//                    if (input.isParkManagement()) roles.add(serverRoles.get(searchableRoles.indexOf("management")));
//                    if (input.isAdmin()) roles.add(serverRoles.get(searchableRoles.indexOf("admin")));
                    keycloak.realm("rvpark").users().get(userID).roles().realmLevel().add(roles);
                    response.close();
                    keycloak.close();
                    // Storing extra information in RVPark tables.
                    userService.register(input, userID);
                    return new ResponseEntity<>(ResultEntity.successWithData(userID, "Success!"), HttpStatus.OK);
                }
                keycloak.close();
                response.close();
            }
        } catch (Exception e) {
            return new ResponseEntity<>(ResultEntity.successWithOutData("Internal Server Error!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(ResultEntity.failed("Bad Request!"), HttpStatus.BAD_REQUEST);
    }

    /**
     * Logs a user in.
     * @param input The login request.
     * @return If login is successful, an access token and a refresh token. If not, access token and refresh token will not be applicable.
     */
    @PostMapping(value = "/login")
    @ResponseBody
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest input) {
        try {
            WebClient client = WebClient.create();
            ObjectMapper jsonMapper = new ObjectMapper();
            String tokenResponse = client.post()
                                    .uri(AuthCredentials.TOKEN_ENDPOINT)
                                    .header(HttpHeaders.CONTENT_TYPE, AuthCredentials.USER_LOGIN_CONTENT_TYPE)
                                    .body(BodyInserters.fromFormData("username", input.getUsername())
                                                        .with("password", input.getPassword())
                                                        .with("grant_type", AuthCredentials.USER_LOGIN_GRANT_TYPE)
                                                        .with("client_id", AuthCredentials.USER_LOGIN_CLIENT_ID)
                                                        .with("client_secret", AuthCredentials.USER_LOGIN_CLIENT_SECRET))
                                    .exchangeToMono(response ->{
                                        if (response.statusCode().is4xxClientError())
                                            return response.bodyToMono(String.class);
                                        return response.bodyToMono(String.class);
                                    })
                                    .block();
            LoginResponse loginResponse = new LoginResponse(jsonMapper.readTree(tokenResponse));
            if (loginResponse.getExpiry().equals("0"))
                return new ResponseEntity<>(loginResponse, HttpStatus.UNAUTHORIZED);
            return new ResponseEntity<>(loginResponse, HttpStatus.OK);
        } catch (Exception ignored) {}
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Receives the user's email and sends a forgot email password.
     * @param forgotPassword Forgot password request.
     * @return Always returns "Success"
     */
    @PostMapping(value = "/forgot")
    @ResponseBody
    public ResponseEntity<ResultEntity<String>> forgotPassword(@RequestBody ForgotPassword forgotPassword) {
        try {
            userService.forgotPassword(forgotPassword.getEmail());
        } catch (Exception ignored) {}
        return new ResponseEntity<>(ResultEntity.successWithOutData("Success!"), HttpStatus.OK);
    }

    /**
     * Resets a user's password.
     * @param token The token sent to the user to reset their password.
     * @param resetPassword Reset password request.
     * @return Returns "Password was not changed" if the operation was unsuccessful, and "Success" otherwise.
     */
    @PostMapping(value = "/reset/{token}")
    @ResponseBody
    public ResponseEntity<String> resetPassword(@PathVariable String token, @RequestBody ResetPassword resetPassword) {
        String userId = userService.resetPassword(token, resetPassword);
        if(userId.equals("false"))
            return new ResponseEntity<>("Password was not changed", HttpStatus.UNAUTHORIZED);
        try {
                Keycloak keycloak = getKeycloak();
                if (keycloak != null) {
                    CredentialRepresentation credential = new CredentialRepresentation();
                    credential.setType(CredentialRepresentation.PASSWORD);
                    credential.setValue(resetPassword.getPassword());
                    credential.setTemporary(false);
                    keycloak.realm("rvpark").users().get(userId).resetPassword(credential);
                    keycloak.close();
                    return new ResponseEntity<>("Success!", HttpStatus.OK);
                }
        } catch (Exception ignored) {}
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Returns a new access token and refresh token in exchange with a refresh token.
     * @param input The refresh request.
     * @return The new set of access token and refresh token.
     */
    @PostMapping(value = "/refresh")
    @ResponseBody
    public ResponseEntity<LoginResponse> refresh(@RequestBody RefreshRequest input) {
        try {
            WebClient client = WebClient.create();
            ObjectMapper jsonMapper = new ObjectMapper();
            String tokenResponse = client.post()
                    .uri(AuthCredentials.TOKEN_ENDPOINT)
                    .header(HttpHeaders.CONTENT_TYPE, AuthCredentials.USER_LOGIN_CONTENT_TYPE)
                    .body(BodyInserters.fromFormData("refresh_token", input.getRefreshToken())
                            .with("grant_type", AuthCredentials.USER_REFRESH_GRANT_TYPE)
                            .with("client_id", AuthCredentials.USER_LOGIN_CLIENT_ID)
                            .with("client_secret", AuthCredentials.USER_LOGIN_CLIENT_SECRET))
                    .exchangeToMono(response ->{
                        if (response.statusCode().is4xxClientError())
                            return response.bodyToMono(String.class);
                        return response.bodyToMono(String.class);
                    })
                    .block();
            LoginResponse loginResponse = new LoginResponse(jsonMapper.readTree(tokenResponse));
            if (loginResponse.getExpiry().equals("0"))
                return new ResponseEntity<>(loginResponse, HttpStatus.UNAUTHORIZED);
            return new ResponseEntity<>(loginResponse, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Returns an instance of Keycloak object.
     * @return An instance of Keycloak object.
     * @throws JsonProcessingException When the response from Keycloak server cannot be parsed.
     */
    private Keycloak getKeycloak() throws JsonProcessingException {
        WebClient client = WebClient.create();
        ObjectMapper jsonMapper = new ObjectMapper();
        String tokenResponse = client.post()
                .uri(AuthCredentials.TOKEN_ENDPOINT)
                .header(HttpHeaders.CONTENT_TYPE, AuthCredentials.USER_LOGIN_CONTENT_TYPE)
                .body(BodyInserters.fromFormData("grant_type", AuthCredentials.REGISTRATION_GRANT_TYPE)
                        .with("client_id", AuthCredentials.REGISTRATION_CLIENT_ID)
                        .with("client_secret", AuthCredentials.REGISTRATION_CLIENT_SECRET))
                .exchangeToMono(response ->{
                    if (response.statusCode().is4xxClientError())
                        return response.bodyToMono(String.class);
                    return response.bodyToMono(String.class);
                })
                .block();
        JsonNode accessToken = jsonMapper.readTree(tokenResponse);
        if (accessToken.has("access_token"))
            return Keycloak.getInstance(
                    "http://auth:8080/auth",
                    "rvpark",
                    "admin-cli",
                    accessToken.get("access_token").toString().replace("\"", "")
            );
        else
            return null;
    }
}