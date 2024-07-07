package com.backend.studyworld.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRes {
    private String id;

    private int courseId;

    private int userId;

    private Long totalAmount;

    private Long discountAmount;

    private Date createdTime;

    private Date updateTime;

    private String status;

    private String courseName;

    private String transactionNo;

    private String bankCode;

    public OrderRes(int courseId, int userId, Long discountAmount,  String status, String courseName, String transactionNo, String bankCode) {
        this.courseId = courseId;
        this.userId = userId;
        this.discountAmount =discountAmount;
        this.status = status;
        this.courseName = courseName;
        this.transactionNo = transactionNo;
        this.bankCode = bankCode;
    }
}
