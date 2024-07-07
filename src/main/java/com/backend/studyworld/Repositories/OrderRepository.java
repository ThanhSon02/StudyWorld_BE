package com.backend.studyworld.Repositories;

import com.backend.studyworld.DTO.response.OrderRes;
import com.backend.studyworld.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    @Query("select new com.backend.studyworld.DTO.response.OrderRes(o.courseId,o.userId,o.discountAmount,o.status,c.courseName, o.transactionNo, o.bankCode) from Order o join Course c on o.courseId = c.id")
    List<OrderRes> getAllOrder();
}
