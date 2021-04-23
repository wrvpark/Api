package com.wrvpark.apiserver.dto;

import com.wrvpark.apiserver.domain.Forum;
import com.wrvpark.apiserver.util.TimeUtil;
import java.io.Serializable;

/**
 * @author Vahid Haghighat
 */
public class MiniForumPostDTO implements Serializable {
    private String id;
    private String title;
    private UserDTO creator;
    private String createDate;

    public MiniForumPostDTO() {}

    public MiniForumPostDTO(Forum post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.creator = new UserDTO(post.getCreator());
        this.createDate = TimeUtil.convertDateToString(post.getCreateDate());
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public UserDTO getCreator() {
        return creator;
    }

    public String getCreateDate() {
        return createDate;
    }
}
