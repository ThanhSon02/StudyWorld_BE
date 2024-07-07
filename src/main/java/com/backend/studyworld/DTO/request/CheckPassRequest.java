package com.backend.studyworld.DTO.request;

import lombok.Data;

@Data
public class CheckPassRequest {
    private String password;
    private String type;
}
