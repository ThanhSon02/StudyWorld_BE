package com.backend.studyworld.Controllers;

import com.backend.studyworld.DTO.response.ApiBaseResponse;
import com.backend.studyworld.DTO.response.OrderRes;
import com.backend.studyworld.Services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @GetMapping("/api/order/getAll")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiBaseResponse> getAllOrders() {
        ApiBaseResponse response = new ApiBaseResponse();
        List<OrderRes> orderResList = orderService.getAllOrders();
        Map<String, Object> map = new HashMap<>();
        map.put("allOrders", orderResList);
        response.setHttpStatus(HttpStatus.OK);
        response.setData(map);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
