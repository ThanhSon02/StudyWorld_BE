package com.backend.studyworld.Services;

import com.backend.studyworld.Model.OTP;
import com.backend.studyworld.Repositories.OTPRepository;
import com.backend.studyworld.exceptions.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class OTPService {
    @Autowired
    private OTPRepository otpRepository;

    public String generateOTP(int userId, boolean used) {
        String otpCode = new DecimalFormat("000000").format(new Random().nextInt(999999));
        OTP otpGenerated = otpRepository.save(new OTP(otpCode, new Date(), new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(1)), userId, false));
        return otpGenerated.getOtp();
    }

    public boolean checkOTP(int userId, String otp) {
        OTP otpFindByUserID = otpRepository.findOTPByUser(userId);
        if (otpFindByUserID.getOtp().equals(otp)) {
            Date currentDate = new Date();
            if (currentDate.after(otpFindByUserID.getCreateAt()) && currentDate.before(otpFindByUserID.getTimeExpired()) && !otpFindByUserID.isUsed()) {
                otpFindByUserID.setUsed(true);
                otpRepository.save(otpFindByUserID);
                return true;
            }
        } else {
            throw new InternalException("OTP không tồn tại");
        }
        return false;
    }
}
