package com.backend.studyworld.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "otp")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OTP {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String otp;

    private Date createAt;

    private Date timeExpired;

    private int userId;

    private boolean used;

    public OTP(String otp, Date createAt, Date timeExpired, int userId, boolean used) {
        this.otp = otp;
        this.createAt = createAt;
        this.timeExpired = timeExpired;
        this.userId = userId;
        this.used = used;
    }
}
