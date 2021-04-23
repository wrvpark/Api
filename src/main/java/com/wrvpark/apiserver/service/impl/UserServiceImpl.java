package com.wrvpark.apiserver.service.impl;

import com.wrvpark.apiserver.domain.Notification;
import com.wrvpark.apiserver.domain.Role;
import com.wrvpark.apiserver.domain.User;
import com.wrvpark.apiserver.dto.FullUserInformationDTO;
import com.wrvpark.apiserver.dto.requests.authentication.AdminUpdateUserRequest;
import com.wrvpark.apiserver.dto.requests.authentication.RegistrationRequest;
import com.wrvpark.apiserver.dto.requests.authentication.ResetPassword;
import com.wrvpark.apiserver.repository.NotificationRepository;
import com.wrvpark.apiserver.repository.UserRepository;
import com.wrvpark.apiserver.service.UserService;
import com.wrvpark.apiserver.util.CommActions;
import com.wrvpark.apiserver.util.EmailUtil;
import com.wrvpark.apiserver.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Vahid Haghighat
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private  NotificationRepository notificationRepository;

    @Override
    public ResultEntity<Boolean> register(RegistrationRequest request, String id) {
        User user = new User(request, id);
        Notification notification = new Notification(request);
        notification = notificationRepository.save(notification);
        user.setNotification(notification);
        userRepository.save(user);
        return null;
    }

    @Override
    public int forgotPassword(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null)
            return 200;
        String token = UUID.randomUUID().toString();
        user.setToken(token);
        userRepository.save(user);
        String content = "Click <a href=\"" + CommActions.generateResetPasswordURL(token) + "\">here</a> to reset your password!";
        return EmailUtil.send("reset@wrvpark.com", email, "Reset Password", content);
    }

    @Override
    public String resetPassword(String token, ResetPassword resetPassword) {
        User user = userRepository.findByToken(token);
        if(!user.getEmail().equalsIgnoreCase(resetPassword.getEmail()) ||
           !resetPassword.getPassword().equals(resetPassword.getConfirmPassword()))
            return "false";
        user.setToken(null);
        userRepository.save(user);
        return user.getId();
    }

    // Admin Section Methods
    @Override
    public ResultEntity<List<FullUserInformationDTO>> getAll() {
        List<User> users = userRepository.findAll();
        List<FullUserInformationDTO> userDTOS = new ArrayList<>();
        for(User user : users)
            userDTOS.add(new FullUserInformationDTO(user));
        return ResultEntity.successWithData(userDTOS, "Success!");
    }

    @Override
    public ResultEntity<List<FullUserInformationDTO>> getAllIds(List<String> userIds) {
        List<User> users = userRepository.findAllByIdIn(userIds);
        List<FullUserInformationDTO> userDTOS = new ArrayList<>();
        for (User user : users)
            userDTOS.add(new FullUserInformationDTO(user));
        return ResultEntity.successWithData(userDTOS, "Success!");
    }

    @Override
    public ResultEntity<FullUserInformationDTO> updateUser(String id, AdminUpdateUserRequest request) {
        if (userRepository.findById(id).isPresent()) {
            User user = userRepository.findById(id).get();
            user.setRoles(new Role(request));
            user.setLotNo(request.getLotNo());
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
//            Notification notification = user.getNotification();
//            notification.setEvent(request.isEventNotification());
//            notification.setParkDocument(request.isParkDocumentNotification());
//            notification.setSaleRent(request.isSaleRentNotification());
//            notification.setService(request.isServiceNotification());
//            notification.setLostFound(request.isLostFoundNotification());
//            notificationRepository.save(notification);
            user = userRepository.save(user);

            return ResultEntity.successWithData(new FullUserInformationDTO(user), "Success!");
        }
        return ResultEntity.failed("Failed to find the user");
    }

    @Override
    public ResultEntity<FullUserInformationDTO> getUserInfoById(String id) {
        if (userRepository.findById(id).isPresent()){
            User user = userRepository.findById(id).get();
            return ResultEntity.successWithData(new FullUserInformationDTO(user), "Success");
        }
        return ResultEntity.failed("Failed to find the user");
    }
}