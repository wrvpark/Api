package com.wrvpark.apiserver.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wrvpark.apiserver.configuration.AuthCredentials;
import com.wrvpark.apiserver.dto.FullUserInformationDTO;
import com.wrvpark.apiserver.dto.requests.authentication.AdminUpdateUserRequest;
import com.wrvpark.apiserver.service.UserService;
import com.wrvpark.apiserver.util.ResultEntity;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Vahid Haghighat
 * Desciprion: Handles requests for Admin section of the website.
 */

@CrossOrigin
@RestController
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    /**
     * Returns a list of all users.
     * @return a list of all users.
     */
    @GetMapping("/users")
    @ResponseBody
    public ResponseEntity<ResultEntity<List<FullUserInformationDTO>>> getAllUsers() {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    /**
     * Returns a list of all users in a specific role.
     * @param role The role that its users should be returned.
     * @return A list of all users in a specific role.
     */
    @GetMapping("/users/{role}")
    @ResponseBody
    public ResponseEntity<ResultEntity<List<FullUserInformationDTO>>> getAllInARole(@PathVariable String role) {
        try (Keycloak keycloak = getKeycloak()) {
            assert keycloak != null;
            Set<UserRepresentation> kUsers = keycloak.realm("rvpark").roles().get(role.toLowerCase()).getRoleUserMembers();
            List<String> userIds = new ArrayList<>();
            for (UserRepresentation kUser : kUsers)
                userIds.add(kUser.getId());
            return new ResponseEntity<>(userService.getAllIds(userIds), HttpStatus.OK);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Returns a specific user.
     * @param id The id of the user to be returned.
     * @return A specific user.
     */
    @GetMapping("/user/{id}")
    @ResponseBody
    public ResponseEntity<ResultEntity<FullUserInformationDTO>> getUserInfo(@PathVariable String id) {
        return new ResponseEntity<>(userService.getUserInfoById(id), HttpStatus.OK);
    }

    /**
     * Updates a specific user.
     * @param id The id of the user to be updated.
     * @param request The parameters of the user to be updated.
     * @return The updated user object.
     */
    @PostMapping("/user/{id}")
    @ResponseBody
    public ResponseEntity<ResultEntity<FullUserInformationDTO>> updateUser(@PathVariable String id, @RequestBody AdminUpdateUserRequest request) {
        try (Keycloak keycloak = getKeycloak()) {
            assert keycloak != null;

            List<RoleRepresentation> serverRoles = keycloak
                    .realm("rvpark")
                    .roles()
                    .list();
            List<String> searchableRoles = serverRoles
                    .stream()
                    .map(RoleRepresentation::getName)
                    .collect(Collectors.toList());
            List<RoleRepresentation> allRoles = new ArrayList<>();
            allRoles.add(serverRoles.get(searchableRoles.indexOf("admin")));
            allRoles.add(serverRoles.get(searchableRoles.indexOf("board")));
            allRoles.add(serverRoles.get(searchableRoles.indexOf("owner")));
            allRoles.add(serverRoles.get(searchableRoles.indexOf("renter")));
            allRoles.add(serverRoles.get(searchableRoles.indexOf("management")));
            allRoles.add(serverRoles.get(searchableRoles.indexOf("visitor")));
            allRoles.add(serverRoles.get(searchableRoles.indexOf("unapproved")));

            UserRepresentation kUser = keycloak.realm("rvpark").users().get(id).toRepresentation();
            List<RoleRepresentation> roles = new ArrayList<>();
            if (request.isAdmin()) roles.add(serverRoles.get(searchableRoles.indexOf("admin")));
            if (request.isBoardMember()) roles.add(serverRoles.get(searchableRoles.indexOf("board")));
            if (request.isOwner()) roles.add(serverRoles.get(searchableRoles.indexOf("owner")));
            if (request.isRenter()) roles.add(serverRoles.get(searchableRoles.indexOf("renter")));
            if (request.isParkManagement()) roles.add(serverRoles.get(searchableRoles.indexOf("management")));
            if (request.isVisitor()) roles.add(serverRoles.get(searchableRoles.indexOf("visitor")));
            if (request.isUnapproved()) roles.add(serverRoles.get(searchableRoles.indexOf("unapproved")));

            keycloak.realm("rvpark").users().get(id).roles().realmLevel().remove(allRoles);
            keycloak.realm("rvpark").users().get(id).roles().realmLevel().add(roles);

            return new ResponseEntity<>(userService.updateUser(id, request), HttpStatus.OK);
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
        String wellKnownResponse = client.get()
                .uri(AuthCredentials.REALM_WELL_KNOWN_ENDPOINT)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        ObjectMapper jsonMapper = new ObjectMapper();
        JsonNode wellKnown = jsonMapper.readTree(wellKnownResponse);
        String tokenEndpoint = wellKnown.get("token_endpoint").toString().replace("\"", "");

        String tokenResponse = client.post()
                .uri(tokenEndpoint)
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