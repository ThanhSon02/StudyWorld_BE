package com.backend.studyworld.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private int courseId;

    private int userId;

    private String transactionNo;

    private Long totalAmount;

    private Long discountAmount;

    private Date createTime;

    private String bankCode;

    private String status;

    public Order(int courseId, int userId, String transactionNo, Long totalAmount, Long discountAmount, Date createTime, String bankCode, String status) {
        this.courseId = courseId;
        this.userId = userId;
        this.transactionNo = transactionNo;
        this.totalAmount = totalAmount;
        this.discountAmount = discountAmount;
        this.createTime = createTime;
        this.bankCode = bankCode;
        this.status = status;
    }
}
