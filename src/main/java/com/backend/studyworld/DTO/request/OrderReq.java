package com.backend.studyworld.DTO.request;

import lombok.Data;

@Data
public class OrderReq {
    private String id;

    private int courseId;

    private int userId;

    private long totalAmount;

    private String status;
}
