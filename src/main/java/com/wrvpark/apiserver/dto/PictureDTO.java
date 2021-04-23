package com.wrvpark.apiserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 *
 * @author Isabel Ke
 * time:2021-02-17
 *
 * a customized picture dto class to return to the client-side
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PictureDTO {
    private String id;
    //picture name
    private String pictureName;
    private String url;
    private MultipartFile file;

    public PictureDTO(String id, String pictureName, String url) {
        this.id = id;
        this.pictureName = pictureName;
       this.url=url;
    }
}
