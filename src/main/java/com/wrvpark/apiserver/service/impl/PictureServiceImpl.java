package com.wrvpark.apiserver.service.impl;

import com.wrvpark.apiserver.domain.Picture;
import com.wrvpark.apiserver.dto.PictureDTO;
import com.wrvpark.apiserver.repository.PictureRepository;
import com.wrvpark.apiserver.service.FileUploadingService;
import com.wrvpark.apiserver.service.PictureService;
import com.wrvpark.apiserver.util.CommActions;
import com.wrvpark.apiserver.util.ConstantUtil;
import com.wrvpark.apiserver.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author Isabel Ke
 * Original date:2020-02-17
 *
 * Description:picture service class that handles all the picture logic
 */
@Service
public class PictureServiceImpl implements PictureService {

    @Autowired
    private PictureRepository pictureRepository;
    @Autowired
    private FileUploadingService fileUploadingService;
    //the upload dir
    private String uploadDir = ConstantUtil.uploadFolder();

    /**
     * get all pictures
     * @return a customized list of pictures if pictures exist, otherwise return an error message.
     */
    @Override
    public ResultEntity<List<PictureDTO>> getAllPictures(String message) {
        List<Picture> pictures=pictureRepository.findAll();
        //check if there is any pictures
        if(pictures.size() == 0 || pictures == null)
        {
            return ResultEntity.successWithOutData("No pictures!");
        }
        //has pictures
        //convert the pictures to  a list of picture dto
        List<PictureDTO> pictureDTOS=pictures.stream().map(picture -> {
            //get the downloading url
            String url=CommActions.generateURL(picture.getPictureName());
            return new PictureDTO(picture.getId(),picture.getPictureName(),url);

        }).collect(Collectors.toList());
        return  ResultEntity.successWithData(pictureDTOS,message);
    }


    /**
     * add a new picture to the database
     * @param pictures picture that will be added
     * @return a customized list of pictures if pictures after adding the picture
     */
    @Override
    public ResultEntity<List<PictureDTO>> addPicture(MultipartFile[] pictures) {
        //store all the pictures in the server
        List<String> pics=CommActions.storeFile(pictures);
        //store the pictures in the database

        for (int i = 0; i < pics.size(); i++) {
            Picture p=new Picture(pics.get(i),uploadDir);
            pictureRepository.save(p);
        }
        //if success, get all the pictures
        return getAllPictures("Add new pictures!");
    }


    /**
     * delete a picture by its id
     * @param id of the pictue that will be deleted
     * @return an error message if fails. Otherwise a customized list of pictures if pictures.
     */
    @Override
    public ResultEntity<List<PictureDTO>> deletePictureById(String id) {

            Picture picture=pictureRepository.findById(id).get();
            //delete this picture from the database
            pictureRepository.deleteById(id);
            //delete it from the server folder
            String pictureName=picture.getPictureName();
            String disk=picture.getUploadDir();
            String path=disk+"/"+pictureName;
        try {
            Path fileToDeletePath = Paths.get(path);
            Files.delete(fileToDeletePath);
        } catch (IOException e) {
           return ResultEntity.failed(e.getMessage());
        }
        return getAllPictures("Deleted a picture");
    }
}
