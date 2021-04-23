package com.wrvpark.apiserver.service;

import com.wrvpark.apiserver.domain.Forum;
import com.wrvpark.apiserver.domain.Response;
import com.wrvpark.apiserver.dto.*;
import com.wrvpark.apiserver.dto.requests.DeleteDTO;
import com.wrvpark.apiserver.dto.requests.ForumSearch;
import com.wrvpark.apiserver.dto.requests.NewForumPost;
import com.wrvpark.apiserver.util.ResultEntity;
import java.util.List;

/**
 * @author Vahid Haghighat
 */
public interface ForumService {
    ResultEntity<List<ForumPostDTO>> getAllForumPosts();
    ResultEntity<List<ForumPostDTO>> getAllForumPostsByType(String type);
    ResultEntity<ForumPostDTO> respondToForumPost(Response response);
    ResultEntity<ForumPostDTO> getPostById(String id);
    ResultEntity<ForumPostDTO> deletePost(DeleteDTO dto);
    ResultEntity<List<MiniForumPostDTO>> searchPost(ForumSearch search);
    ResultEntity<ForumPostDTO> newPost(Forum post);
    ResultEntity<ForumPostDTO> updatePost(String postId, NewForumPost newPost);
    boolean checkPermission(String postId, String editorId);
}
