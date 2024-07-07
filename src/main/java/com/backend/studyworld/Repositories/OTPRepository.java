package com.backend.studyworld.Repositories;

import com.backend.studyworld.Model.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OTPRepository extends JpaRepository<OTP, Integer> {

    @Query(value = "select * from otp  where user_id = ?1 and create_at > now() - interval 3 minute", nativeQuery = true)
    public OTP findOTPByUser(int userId);
}
