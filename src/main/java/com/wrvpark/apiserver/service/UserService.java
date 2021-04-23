package com.wrvpark.apiserver.service;

import com.wrvpark.apiserver.dto.FullUserInformationDTO;
import com.wrvpark.apiserver.dto.requests.authentication.AdminUpdateUserRequest;
import com.wrvpark.apiserver.dto.requests.authentication.RegistrationRequest;
import com.wrvpark.apiserver.dto.requests.authentication.ResetPassword;
import com.wrvpark.apiserver.util.ResultEntity;
import java.util.List;

/**
 * @author Vahid Haghighat
 */
public interface UserService {
    ResultEntity<Boolean> register(RegistrationRequest request, String id);
    int forgotPassword(String email);
    String resetPassword(String token, ResetPassword resetPassword);
    ResultEntity<List<FullUserInformationDTO>> getAll();
    ResultEntity<List<FullUserInformationDTO>> getAllIds(List<String> userIds);
    ResultEntity<FullUserInformationDTO> updateUser(String id, AdminUpdateUserRequest request);
    ResultEntity<FullUserInformationDTO> getUserInfoById(String Id);
}
