package com.wrvpark.apiserver.controllers;

import com.wrvpark.apiserver.service.FileUploadingService;
import com.wrvpark.apiserver.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

/**
 * @author Isabel Ke
 * Original date:2020-02-07
 *
 * Description:controller that responsible for uploading images/files to the server folder
 */
@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("/files")
public class FileUploadingController {

    @Autowired
    private FileUploadingService fileUploadingService;

    /**
     * upload files/images and store them in the server folder
     * @param files that will be uploaded
     * @return a list of new uploaded image/file names
     */
    @PostMapping("")
    public ResultEntity<List<String>> uploadFilesToServer(@RequestParam("files") MultipartFile[] files)
    {
        return fileUploadingService.uploadFiles(files);
    }

    /**
     * delete an image or file from the server folder
     * @param fileName that will be deleted
     * @return true if the deletion is successful, false if it fails
     */

    @DeleteMapping("/{fileName}")
    public ResultEntity<Boolean> deleteFiles(@PathVariable String fileName)
    {
        return fileUploadingService.deleteFileFromServer(fileName);
    }
}
