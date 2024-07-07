package com.backend.studyworld.Services;

import com.backend.studyworld.Model.Email;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Autowired
    private OTPService otpService;

    public boolean sendMail(Email email) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(sender);
            helper.setTo(email.getToEmail());
            helper.setSubject(email.getSubject());
            message.setContent(email.getBody(), "text/html; charset=utf-8");
            mailSender.send(message);
            return true;
        }catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String generateChangePassOTPConfirmEmail(int userId) {
        String otp = otpService.generateOTP(userId, false);
        String msg = "<p>OTP xác nhận đổi mật khẩu: <strong>"+ otp + "</strong></p>";
        return msg;
    }

    public String generatePaymentConfirmEmail(String courseName) {
        String msg = "<p>Bạn đã thanh toán thành công <strong>Khoá học " + courseName + "</strong>&nbsp;</p>";
        return msg;
    }
}
