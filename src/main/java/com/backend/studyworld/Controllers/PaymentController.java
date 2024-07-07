package com.backend.studyworld.Controllers;

import com.backend.studyworld.DTO.request.CourseReq;
import com.backend.studyworld.DTO.request.PaymentReq;
import com.backend.studyworld.DTO.request.UserRequest;
import com.backend.studyworld.DTO.response.*;
import com.backend.studyworld.Services.EnrollmentServive;
import com.backend.studyworld.Services.OrderService;
import com.backend.studyworld.Services.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin("*")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @Autowired
    private EnrollmentServive enrollmentServive;

    @Autowired
    private OrderService orderService;

    @GetMapping("/api/payment/vn-pay")
    public ResponseEntity<ApiBaseResponse> pay(HttpServletRequest request) {
        ApiBaseResponse response = new ApiBaseResponse();
        PaymentRes paymentRes = paymentService.createVnPayPayment(request);
        Map map = new HashMap();
        map.put("paymentData", paymentRes);
        response.setData(map);
        response.setHttpStatus(HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/api/payment/vn-pay-callback")
    public ResponseEntity<ApiBaseResponse> payCallbackHandler(HttpServletRequest request, @RequestBody PaymentReq paymentReq) {
        String status = request.getParameter("vnp_ResponseCode");
        ApiBaseResponse response = new ApiBaseResponse();
        if (status.equals("00")) {
            String transactionNo = request.getParameter("vnp_TransactionNo");
            String bankCode = request.getParameter("vnp_BankCode");
            List<CourseReq> courseResList = paymentReq.getOrders();
            UserRequest userRequest = paymentReq.getUserInfo();
            Long totalAmount = paymentReq.getTotalAmount();
            Long discountAmount = paymentReq.getDiscountAmount();
            for (CourseReq courseReq : courseResList) {
                orderService.createOrder(courseReq.getId(), userRequest.getId(), transactionNo,totalAmount,discountAmount, new Date(), bankCode,status);
                enrollmentServive.enroll(courseReq.getId(), userRequest.getId(), 0, new Date());
            }
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("Thành công");
            Map map = new HashMap();
            map.put("status", "00");
            response.setData(map);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            String transactionNo = request.getParameter("vnp_TransactionNo");
            String bankCode = request.getParameter("vnp_BankCode");
            List<CourseReq> courseResList = paymentReq.getOrders();
            UserRequest userRequest = paymentReq.getUserInfo();
            Long totalAmount = paymentReq.getTotalAmount();
            Long discountAmount = paymentReq.getDiscountAmount();
            for (CourseReq courseReq : courseResList) {
                orderService.createOrder(courseReq.getId(), userRequest.getId(), transactionNo,totalAmount,discountAmount, new Date(), bankCode,status);
            }
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            response.setMessage("Thất bại");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
