package com.wrvpark.apiserver.service.impl;


import com.wrvpark.apiserver.service.FileUploadingService;
import com.wrvpark.apiserver.util.CommActions;
import com.wrvpark.apiserver.util.ConstantUtil;
import com.wrvpark.apiserver.util.ResultEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


/**
 * @author Isabel Ke
 * Original date:2020-03-07
 *
 * Description:file uploading service class that handles all the file uploading logic
 */
@Service
public class FileUploadingServiceImpl implements FileUploadingService {
    //the folder that stored the files on the server
   private final String realPath = ConstantUtil.uploadFolder();


    /**
     *upload files/images to the server folder
     * @param files
     * @return
     */
    @Override
    public ResultEntity<List<String>> uploadFiles(MultipartFile[] files) {
        List<String> fileNames=CommActions.storeFile(files);
        //if success, get all the pictures
        return ResultEntity.successWithData(fileNames,"");
    }

    /**
     * delete files from server folder
     * @param fileName
     * @return
     */
    @Override
    public ResultEntity<Boolean> deleteFileFromServer(String fileName) {
        //delete it from the server folder
        String path=realPath+"/"+fileName;
        try {
            Path fileToDeletePath = Paths.get(path);
            Files.delete(fileToDeletePath);
            return ResultEntity.successWithOutData("Deleted!");
        } catch (IOException e) {
            return ResultEntity.failed(e.getMessage());
        }
    }
}
