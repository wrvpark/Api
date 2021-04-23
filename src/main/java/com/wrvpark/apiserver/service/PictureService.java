package com.wrvpark.apiserver.service;


import com.wrvpark.apiserver.dto.PictureDTO;
import com.wrvpark.apiserver.util.ResultEntity;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;



/**
 * @author Isabel Ke
 * Original date:2020-02-17
 *
 * Description:picture service class that handles all the picture logic
 */
public interface PictureService {
    //get all pictures
    ResultEntity<List<PictureDTO>> getAllPictures(String message);
    //add a new picture
    ResultEntity<List<PictureDTO>> addPicture(MultipartFile[] pictures);
    //delete a picture by id
    ResultEntity<List<PictureDTO>> deletePictureById(String id);
}
