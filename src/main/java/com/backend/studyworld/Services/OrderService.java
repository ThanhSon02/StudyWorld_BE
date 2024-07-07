package com.backend.studyworld.Services;

import com.backend.studyworld.DTO.response.OrderRes;
import com.backend.studyworld.Model.Order;
import com.backend.studyworld.Repositories.CourseRepository;
import com.backend.studyworld.Repositories.OrderRepository;
import com.backend.studyworld.Repositories.UserRepository;
import com.backend.studyworld.exceptions.InternalException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    private ModelMapper modelMapper = new ModelMapper();
    public OrderRes createOrder(int courseId, int userId, String transactionNo, Long totalAmount, Long discountAmount, Date createTime, String bankCode, String status) {
        try {
            Order order = orderRepository.save(new Order(courseId,userId,transactionNo,totalAmount,discountAmount,createTime,bankCode,status));
            OrderRes orderRes = modelMapper.map(order, OrderRes.class);
            return orderRes;
        }catch (Exception e) {
            e.printStackTrace();
            throw new InternalException("Lỗi hệ thống");
        }
    }

    public List<OrderRes> getAllOrders() {
        List<OrderRes> orderResList = orderRepository.getAllOrder();
        return orderResList;
    }
}
