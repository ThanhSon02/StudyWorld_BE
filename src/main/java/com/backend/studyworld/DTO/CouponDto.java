package com.backend.studyworld.DTO;

import lombok.Data;

@Data
public class CouponDto {
    private int id;

    private String couponName;

    private String couponCode;

    private int amount;

    private String createAt;

    private String timeExpired;
}
