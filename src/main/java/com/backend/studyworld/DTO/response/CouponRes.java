package com.backend.studyworld.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponRes {
    private int id;

    private String couponName;

    private String couponCode;

    private int discount;

    private int amount;

    private Date createAt;

    private Date startTime;

    private Date endTime;
}
