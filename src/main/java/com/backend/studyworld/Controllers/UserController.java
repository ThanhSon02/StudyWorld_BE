package com.backend.studyworld.Controllers;

import com.backend.studyworld.DTO.response.UserRes;
import com.backend.studyworld.Model.Email;
import com.backend.studyworld.Model.User;
import com.backend.studyworld.DTO.request.ChangePasswordRequest;
import com.backend.studyworld.DTO.request.CheckPassRequest;
import com.backend.studyworld.DTO.request.UserRequest;
import com.backend.studyworld.DTO.response.ApiBaseResponse;
import com.backend.studyworld.Services.EmailService;
import com.backend.studyworld.Services.OTPService;
import com.backend.studyworld.Services.UserService;
import com.backend.studyworld.exceptions.ExistException;
import com.backend.studyworld.exceptions.InternalException;
import com.backend.studyworld.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private OTPService otpService;

    @PutMapping("/update")
    public ResponseEntity<ApiBaseResponse> updateUser(@RequestBody UserRequest request) {
        ApiBaseResponse response = new ApiBaseResponse();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        if(user != null) {
            user.setEmail(request.getEmail());
            user.setPhone(request.getPhone());
            user.setName(request.getName());
            Date dateOfBirth = new Date(request.getDateOfBirth());
            user.setDateOfBirth(dateOfBirth);
            userService.save(user);
            UserRes userResponse = new UserRes(user.getId(),user.getEmail(),user.getDateOfBirth(), user.getPhone(), user.getName(),user.getRoles());
            response.setMessage("Update successfully");
            response.setHttpStatus(HttpStatus.OK);
            Map<String, Object> map = new HashMap<>();
            map.put("user", userResponse);
            response.setData(map);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            throw new NotFoundException("Người dùng không tồn tại");
        }
    }

    @PostMapping("/password")
    public ResponseEntity<ApiBaseResponse> changePassword(@RequestBody ChangePasswordRequest request) {
        ApiBaseResponse response = new ApiBaseResponse();
        if(!StringUtils.isEmpty(request.getOldPassword()) && !StringUtils.isEmpty(request.getNewPassword())) {
            boolean changePasswordFlag = userService.changPassword(request.getEmail(),request.getOldPassword(), request.getNewPassword());
            boolean checkOtp = otpService.checkOTP(request.getUserId(), request.getOtp());
            if(changePasswordFlag & checkOtp) {
                response.setMessage("Thay đổi mật khẩu thành công!");
                response.setHttpStatus(HttpStatus.OK);
            } else {
                throw new InternalException("Thay đổi mật khẩu thất bại");
            }
        } else {
            throw new NotFoundException("Mật khẩu không được để trống");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/check_password")
    public ResponseEntity<ApiBaseResponse> checkPass(@RequestBody CheckPassRequest request) {
        ApiBaseResponse response = new ApiBaseResponse();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        boolean checkPass = userService.checkPass(user.getEmail(), request.getPassword());
        Map<String, Object> map = new HashMap<>();
        if(checkPass) {
            map.put("checkPass", true);
            response.setMessage("Mật khẩu chính xác");
            response.setHttpStatus(HttpStatus.OK);
            response.setData(map);
            if(request.getType().equals("01")) {
                String emailText = emailService.generateChangePassOTPConfirmEmail(user.getId());
                emailService.sendMail(new Email(user.getEmail(), "Thay đổi mật khẩu", emailText));
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            throw new ExistException("Sai mật khẩu");
        }
    }

    @GetMapping("/getAllUser")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiBaseResponse> getAllUser() {
        ApiBaseResponse response = new ApiBaseResponse();
        List<UserRes> allUser = userService.getAllUser();
        if (allUser != null) {
            Map<String , Object> map = new HashMap<>();
            map.put("allUser", allUser);
            response.setData(map);
            response.setHttpStatus(HttpStatus.OK);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            throw new NotFoundException("Không có dữ liệu");
        }
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiBaseResponse> deleteUser(@PathVariable int id) {
        ApiBaseResponse response = new ApiBaseResponse();
        boolean checkDelete = userService.delete(id);
        if(checkDelete) {
            response.setMessage("Xoá thành công!");
            response.setHttpStatus(HttpStatus.OK);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            throw new InternalException("Xoá thất bại");
        }
    }
}
