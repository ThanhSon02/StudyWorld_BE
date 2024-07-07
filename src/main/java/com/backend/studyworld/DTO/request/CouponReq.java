package com.backend.studyworld.DTO.request;

import lombok.Data;

@Data
public class CouponReq {
    private int id;

    private String couponName;

    private String couponCode;

    private int discount;

    private int amount;

    private String createAt;

    private String startTime;

    private String endTime;

}
