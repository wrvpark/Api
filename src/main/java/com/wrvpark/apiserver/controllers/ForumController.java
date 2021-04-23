package com.wrvpark.apiserver.controllers;

import com.wrvpark.apiserver.domain.Forum;
import com.wrvpark.apiserver.domain.Response;
import com.wrvpark.apiserver.dto.*;
import com.wrvpark.apiserver.dto.requests.*;
import com.wrvpark.apiserver.service.ForumService;
import com.wrvpark.apiserver.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

/**
 * @author Vahid Haghighat
 * @author Isabel Ke
 * Description: Handles the requests to /forum endpoint.
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/forum")
public class ForumController {

    @Autowired
    private ForumService forumService;

    /**
     * Returns a list of all forum posts.
     * @return a list of all forum posts.
     */
    @GetMapping(value = "/posts")
    @ResponseBody
    public ResponseEntity<ResultEntity<List<ForumPostDTO>>> getAllPosts() {
        return new ResponseEntity<>(forumService.getAllForumPosts(), HttpStatus.OK);
    }

    /**
     * Adds a new forum post.
     * @param post The post to be added.
     * @param principal The identity of the user.
     * @return The created post.
     */
    @PostMapping(value = "/post")
    @ResponseBody
    public ResponseEntity<ResultEntity<ForumPostDTO>> newPost(@RequestBody NewForumPost post, Principal principal) {
        return new ResponseEntity<>(forumService.newPost(new Forum(post, principal.getName())), HttpStatus.OK);
    }

    /**
     * Returns a list of posts that have a specific type.
     * @param type The type that its posts should be returned.
     * @return A list of posts that have a specific type.
     */
    @GetMapping(value = "/posts/{type}")
    @ResponseBody
    public ResponseEntity<ResultEntity<List<ForumPostDTO>>> getAllByType(@PathVariable("type") String type) {
        return new ResponseEntity<>(forumService.getAllForumPostsByType(type), HttpStatus.OK);
    }

    /**
     * Adds a new response to a specific post.
     * @param response  The response that should be added.
     * @param id The id of the post.
     * @param principal The identity of the responder.
     * @return The post with all its responses.
     */
    @PostMapping(value = "/post/{id}/response")
    @ResponseBody
    public ResultEntity<ForumPostDTO> respondToPost(@RequestBody PostResponseDTO response, @PathVariable String id, Principal principal) {
       return forumService.respondToForumPost(new Response(response, principal.getName(), id));
    }

    /**
     * Returns a specific forum post.
     * @param id The id of the post to be returned.
     * @return A specific forum post.
     */
    @GetMapping(value = "/post/{id}")
    @ResponseBody
    public ResponseEntity<ResultEntity<ForumPostDTO>> readForumPost(@PathVariable("id") String id) {
        return new ResponseEntity<>(forumService.getPostById(id), HttpStatus.OK);
    }

    /**
     * Updates a specific forum post.
     * @param id The if of the post to be updated.
     * @param post The data that should be updated.
     * @param principal The identity of the updater.
     * @return The updated post.
     */
    @PutMapping(value = "/post/{id}")
    @ResponseBody
    public ResponseEntity<ResultEntity<ForumPostDTO>> updateForumPost(@PathVariable String id, @RequestBody NewForumPost post, Principal principal) {
        try {
            if (forumService.checkPermission(id, principal.getName()))
                return new ResponseEntity<>(forumService.updatePost(id, post), HttpStatus.OK);
            return new ResponseEntity<>(ResultEntity.failed("You don't have permission to edit this post!"), HttpStatus.UNAUTHORIZED);
        } catch (Exception ignored) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a specific post.
     * @param id The id of the post to be deleted.
     * @param request The request for deleting the post.
     * @param principal The identity of the requester.
     * @return The deleted post.
     */
    @PutMapping(value = "/postDeleting/{id}")
    @ResponseBody
    public ResponseEntity<ResultEntity<ForumPostDTO>> deleteForumPost(@PathVariable String id, @RequestBody ForumPostDeleteRequest request, Principal principal) {
        if (forumService.checkPermission(id, principal.getName()))
            return new ResponseEntity<>(forumService.deletePost(new DeleteDTO(request, principal.getName(), id)), HttpStatus.OK);
        return new ResponseEntity<>(ResultEntity.failed("You do not have the permission to delete the post!"), HttpStatus.UNAUTHORIZED);
    }

    /**
     * Searches for post with specific title, description or posts that have neen posted in a specific period.
     * @param search The search request object.
     * @return A list of all found posts.
     */
    @GetMapping(value = "/search")
    @ResponseBody
    public ResponseEntity<ResultEntity<List<MiniForumPostDTO>>> searchForPosts(@RequestBody ForumSearch search) {
        return new ResponseEntity<>(forumService.searchPost(search), HttpStatus.OK);
    }
}