package com.wrvpark.apiserver.dto;

import com.wrvpark.apiserver.domain.Forum;
import com.wrvpark.apiserver.domain.Response;
import com.wrvpark.apiserver.util.CommActions;
import com.wrvpark.apiserver.util.ConstantUtil;
import com.wrvpark.apiserver.util.TimeUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vahid Haghighat
 */
public class ForumPostDTO implements Serializable {
    private String id;
    private String title;
    private String type;
    private String details;
    private UserDTO creator;
    private String createDate;
    private boolean isClosed;
    private List<String> file;
    private List<ResponseDTO> responses;

    public ForumPostDTO() {}

    public ForumPostDTO(Forum post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.type = post.getType();
        this.details = post.getDetails();
        this.creator = new UserDTO(post.getCreator());
        this.createDate = TimeUtil.convertDateToString(post.getCreateDate());
        this.isClosed = post.isClosed();
        file = new ArrayList<>();
        if(post.getFile() != null && !post.getFile().isEmpty()) {
            String[] files = post.getFile().split(ConstantUtil.DELIMITER);
            for (String fileString : files) {
                file.add(CommActions.generateURL(fileString));
            }
        }
        this.responses = new ArrayList<>();
        for (Response response : post.getResponses())
            responses.add(new ResponseDTO(response));
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getDetails() {
        return details;
    }

    public UserDTO getCreator() {
        return creator;
    }

    public String getCreateDate() {
        return createDate;
    }

    public List<String> getFile() {
        return file;
    }

    public List<ResponseDTO> getResponses() {
        return responses;
    }

    public boolean isClosed() {
        return isClosed;
    }
}
