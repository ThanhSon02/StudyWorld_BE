package com.backend.studyworld.DTO.request;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String email;
    private String oldPassword;
    private String newPassword;
    private int userId;
    private String otp;
}
