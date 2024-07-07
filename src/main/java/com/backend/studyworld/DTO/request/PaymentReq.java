package com.backend.studyworld.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentReq {
    private List<CourseReq> orders;
    private Long totalAmount;
    private Long discountAmount;
    private UserRequest userInfo;
}
