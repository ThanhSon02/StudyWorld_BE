package com.backend.studyworld.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiBaseResponse {
    private String message;
    private HttpStatus httpStatus;
    private Map<String, Object> data;
}
