package com.backend.studyworld.DTO.request;

import lombok.Data;

@Data
public class UserRequest {
    private int id;
    private String email;
    private String password;
    private String avatar;
    private String gender;
    private String phone;
    private String dateOfBirth;
    private String name;
}
