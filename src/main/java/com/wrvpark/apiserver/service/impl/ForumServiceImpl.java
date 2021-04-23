package com.wrvpark.apiserver.service.impl;

import com.wrvpark.apiserver.domain.Forum;
import com.wrvpark.apiserver.domain.Response;
import com.wrvpark.apiserver.domain.User;
import com.wrvpark.apiserver.dto.ForumPostDTO;
import com.wrvpark.apiserver.dto.MiniForumPostDTO;
import com.wrvpark.apiserver.dto.requests.DeleteDTO;
import com.wrvpark.apiserver.dto.requests.ForumSearch;
import com.wrvpark.apiserver.dto.requests.NewForumPost;
import com.wrvpark.apiserver.repository.*;
import com.wrvpark.apiserver.service.ForumService;
import com.wrvpark.apiserver.util.ResultEntity;
import com.wrvpark.apiserver.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vahid Haghighat
 * @author Isabel Ke
 */
@Service
public class ForumServiceImpl implements ForumService {

    @Autowired
    private ForumRepository forumRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NonParkDocumentLogRepository nonParkDocumentLogRepository;

    @Autowired
    private ResponseRepository responseRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public ResultEntity<List<ForumPostDTO>> getAllForumPosts() {
        List<Forum> posts = forumRepository.findAll(Sort.by(Sort.Direction.DESC, "createDate"));
        if (posts.isEmpty())
            return ResultEntity.successWithOutData("There is no Forum Posts Available!");
        return ResultEntity.successWithData(convertToDTO(posts), "Success!");
    }

    private List<ForumPostDTO> convertToDTO(List<Forum> posts) {
        List<ForumPostDTO> dto = new ArrayList<>();
        for(Forum post : posts)
            dto.add(new ForumPostDTO(post));
        return dto;
    }

    @Override
    public ResultEntity<List<ForumPostDTO>> getAllForumPostsByType(String type) {
        List<Forum> posts = forumRepository.findAll((Specification<Forum>)
                (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("type"), type),
                Sort.by(Sort.Direction.DESC, "createDate"));
        if (posts.isEmpty())
            return ResultEntity.successWithOutData("There is no Forum Posts Available!");
        return ResultEntity.successWithData(convertToDTO(posts), "Success!");
    }

    /**
     * respond to a forum
     * @param response the new response
     * @return a list of forums
     */

    @Override
    public ResultEntity<ForumPostDTO> respondToForumPost(Response response) {
        if (forumRepository.findById(response.getForum().getId()).isEmpty())
            return ResultEntity.failed("There is no forum with that ID");

        if (response.getForum().isClosed())
            return ResultEntity.failed("You cannot respond to this post");

        User responder = userRepository.findById(response.getResponder().getId()).get();
        response.setResponder(responder);
        responseRepository.save(response);
        return getPostById(response.getForum().getId());
    }

    @Override
    public ResultEntity<ForumPostDTO> getPostById(String id) {
        if (forumRepository.findById(id).isPresent()) {
            Forum forum = forumRepository.findById(id).get();
            return ResultEntity.successWithData(new ForumPostDTO(forum), "Success!");
        }
        return ResultEntity.failed("The post cannot be found!");
    }

    @Override
    public ResultEntity<ForumPostDTO> deletePost(DeleteDTO dto) {
        if (forumRepository.findById(dto.getItemId()).isPresent()) {
            Forum forum = forumRepository.findById(dto.getItemId()).get();
            if (forum.isClosed())
                return ResultEntity.failed("This post is already closed!");
            forum.setClosed(true);
            forum = forumRepository.save(forum);
            return ResultEntity.successWithData(new ForumPostDTO(forum), "The post has been closed!");
        }
        return ResultEntity.failed("The post was not found!");


//        record this action if this is admin
//        if(isAdmin)
//        {
//            Category category=categoryRepository.findByName("Forum");
//            User modifier=userRepository.findById(dto.getModifierId()).get();
//            NonParkDocumentLog log=new NonParkDocumentLog(modifier,dto.getReason(),dto.getDescription(),
//                    "DELETE",new Date(),dto.getItemId(),
//                    category
//            );
//            nonParkDocumentLogRepository.save(log);
//        }
    }

    @Override
    public ResultEntity<List<MiniForumPostDTO>> searchPost(ForumSearch search) {
        List<Forum> posts = forumRepository.findForumByTitleInAndDetailsInAndCreateDateAfterAndCreateDateBefore(
                search.getKeywords(),
                search.getKeywords(),
                TimeUtil.convertStringToDate(search.getStartTime()),
                TimeUtil.convertStringToDate(search.getEndTime()));
        if (posts.size() == 0)
            return ResultEntity.successWithOutData("No posts has been found!");
        List<MiniForumPostDTO> result = new ArrayList<>(posts.size());
        for(Forum post : posts)
            result.add(new MiniForumPostDTO(post));
        return ResultEntity.successWithData(result, "Success!");
    }

    @Override
    public ResultEntity<ForumPostDTO> newPost(Forum post) {
        Forum forum = forumRepository.save(post);
        return ResultEntity.successWithData(new ForumPostDTO(forum), "Success!");
    }

    @Override
    public ResultEntity<ForumPostDTO> updatePost(String postId, NewForumPost post) {
        if (forumRepository.findById(postId).isPresent()) {
            Forum forum = forumRepository.findById(postId).get();
            forum.setTitle(post.getTitle());
            forum.setDetails(post.getDetails());
            forum.setType(post.getType());
            forum.setFile(post.getFilesString());
            forum = forumRepository.save(forum);
            return ResultEntity.successWithData(new ForumPostDTO(forum), "Success");
        }
        return ResultEntity.failed("The post was not found!");
    }

    @Override
    public boolean checkPermission(String postId, String editorId) {
        if (forumRepository.findById(postId).isPresent()) {
            Forum forum = forumRepository.findById(postId).get();
            User editor = userRepository.findById(editorId).get();
            return forum.getCreator().getId().equals(editorId) || editor.getRoles().isAdmin();
        }
        return false;
    }
}
