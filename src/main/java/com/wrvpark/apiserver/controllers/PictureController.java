package com.wrvpark.apiserver.controllers;

import com.wrvpark.apiserver.dto.PictureDTO;
import com.wrvpark.apiserver.service.PictureService;
import com.wrvpark.apiserver.util.ConstantUtil;
import com.wrvpark.apiserver.util.ResultEntity;
import com.wrvpark.apiserver.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

/**
 * @author Isabel Ke
 * Original date:2020-02-17
 *
 * Description:picture controller
 */
@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("/pictures")
public class PictureController {
    @Autowired
    private PictureService pictureService;

    /**
     * get all pictures
     * @return a list of pictures or null if no data
     */
    @GetMapping("")
    public ResultEntity<List<PictureDTO>> findAllPictures()
    {
        return pictureService.getAllPictures("Get all pictures");
    }

    /**
     * add a new picture
     * @param pictures that will be added
     * @return a list of pictures if the operation is successful.
     */
    @PostMapping("")
    public ResultEntity<List<PictureDTO>> addPicture(@RequestParam("files") MultipartFile[] pictures,
                                                     Authentication authentication)
    {
        boolean isAdmin= SecurityUtil.hasRole(authentication, ConstantUtil.ROLE_ADMIN);
        //only admin can add a website picture
        if(!isAdmin)
        {
            return ResultEntity.failed("Only admin can upload a picture for the website!");
        }
        return pictureService.addPicture(pictures);
    }


    /**
     * delete a pictue by its id
     * @param id of the picture that will be deleted
     * @return a list of pictures if the operation is successful.
     */
    @DeleteMapping("/{id}")
    public ResultEntity<List<PictureDTO>> deletePictureById(@PathVariable String id)
    {
        return pictureService.deletePictureById(id);
    }
}
