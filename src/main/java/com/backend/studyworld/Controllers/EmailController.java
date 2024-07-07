package com.backend.studyworld.Controllers;

import com.backend.studyworld.DTO.response.ApiBaseResponse;
import com.backend.studyworld.Model.Email;
import com.backend.studyworld.Services.EmailService;
import com.backend.studyworld.exceptions.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/public/api/email")
    public ResponseEntity<ApiBaseResponse> sendEmail(@RequestBody Email email) {
        ApiBaseResponse response = new ApiBaseResponse();
        boolean checkEmailSended = emailService.sendMail(email);
        if (checkEmailSended) {
            response.setMessage("Gửi email thành công");
            response.setHttpStatus(HttpStatus.OK);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            throw new InternalException("Gửi email thất bại");
        }
    }
}
