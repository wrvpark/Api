package com.wrvpark.apiserver.util;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


/**
 * @author Isabel Ke
 * @author Vahid Haghighat
 * Original date:2020-02-18
 *
 * Description:handles common actions
 */
@Component
public class CommActions {


    /**
     * store the uploaded file in the server
     * @param files
     */
    public static List<String> storeFile(MultipartFile[] files)
    {
        List<String> fileNames=new ArrayList<>();

        File folder = new File(ConstantUtil.uploadFolder());

        boolean folderExists = folder.isDirectory();
        if(!folderExists)
            folderExists = folder.mkdirs();

        Arrays.stream(files).forEach(file -> {
            String name = file.getOriginalFilename();
            name= UUID.randomUUID()+"-"+name;

            //store this file on the server under Uploads
            File newFile=new File(folder.getAbsolutePath()+File.separator+name);

            try {
                file.transferTo(newFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            fileNames.add(name);
        });
        return fileNames;
    }


    /***
     * generate accessing URL for a file or a picture
     * @param name of the file or the picture
     * @return a accessing URL in String format
     */
    public static String generateURL(String name)
    {
        if (name == null)
            return "";
        String downloadURL = "";
        if (name.toLowerCase().startsWith("http"))
            downloadURL = name;
        else
            downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/" + ConstantUtil.UPLOAD_FOLDER_NAME)
                .path("/" + name)
                .toUriString();
        return downloadURL;
    }

    public static String generateResetPasswordURL(String token) {
        return "http://wrvpark.com/reset/" + token;
    }

    //check if the user is one of the 4 roles.
    public static boolean checkAllPermission(Authentication authentication)
    {
        List<String> roles=new ArrayList<String>();
        roles.add(ConstantUtil.ROLE_ADMIN);
        roles.add(ConstantUtil.ROLE_BOARD_MEMBER);
        roles.add(ConstantUtil.ROLE_PARK_MANAGEMENT);
        roles.add( ConstantUtil.ROLE_RENTER);

      return SecurityUtil.checkRoles(authentication,roles);
    }

    //check if the user is one of the 3 roles.s
    public static boolean checkParkDocumentPermission(Authentication authentication)
    {
        List<String> roles=new ArrayList<String>();
        roles.add(ConstantUtil.ROLE_ADMIN);
        roles.add(ConstantUtil.ROLE_BOARD_MEMBER);
        roles.add(ConstantUtil.ROLE_PARK_MANAGEMENT);
        return SecurityUtil.checkRoles(authentication,roles);
    }
}