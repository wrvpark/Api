package com.wrvpark.apiserver.service;

import com.wrvpark.apiserver.util.ResultEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * @author Isabel Ke
 * Original date:2020-02-07
 *
 * Description:file uploading service interface
 */
public interface FileUploadingService {
    //upload files
    ResultEntity<List<String>>  uploadFiles(MultipartFile[] pictures);
    //delete files from the server
    ResultEntity<Boolean> deleteFileFromServer(String fileName);
}
