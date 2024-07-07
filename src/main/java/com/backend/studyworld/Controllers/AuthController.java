package com.backend.studyworld.Controllers;

import com.backend.studyworld.Config.CustomUserDetailsService;
import com.backend.studyworld.DTO.response.UserRes;
import com.backend.studyworld.Model.Role;
import com.backend.studyworld.Model.User;
import com.backend.studyworld.DTO.request.LoginRequest;
import com.backend.studyworld.DTO.request.UserRequest;
import com.backend.studyworld.DTO.response.ApiBaseResponse;
import com.backend.studyworld.DTO.response.JwtAuthResponse;
import com.backend.studyworld.Services.RoleService;
import com.backend.studyworld.Services.UserService;
import com.backend.studyworld.Util.JwtUtil;
import com.backend.studyworld.exceptions.AuthException;
import com.backend.studyworld.exceptions.ExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/public/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private RoleService roleService;
    @PostMapping("/register")
    public ResponseEntity<ApiBaseResponse> register(@RequestBody UserRequest registerRequest) {
        ApiBaseResponse response = new ApiBaseResponse();
        if (Boolean.TRUE.equals(userService.existsUserByEmail(registerRequest.getEmail()))) {
            throw new ExistException("Email đã được đăng ký");
        }
        String email = registerRequest.getEmail().toLowerCase();
        String passwordEncode = passwordEncoder.encode(registerRequest.getPassword());
        String phone = registerRequest.getPhone();
        String name = registerRequest.getName();
        Role role = roleService.findById(3);
        Date dateOfBirth = new Date();
        User user = new User(email, passwordEncode, phone,  dateOfBirth, name, new Date());
        user.getRoles().add(role);
        User result = userService.save(user);
        response.setMessage("Đăng ký thành công. Hãy đăng nhập để tiếp tục!");
        response.setHttpStatus(HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiBaseResponse> login(@RequestBody LoginRequest loginRequest) {
        ApiBaseResponse response = new ApiBaseResponse();
        User user = userService.findUserByEmail(loginRequest.getEmail());
        if (user ==  null) {
            throw new ExistException("Email chưa được đăng ký");
        }
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            throw new AuthException("Sai mật khẩu");
        }
        User userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        String jwt = jwtUtil.generateToken(userDetails);
        UserRes userResponse = new UserRes(userDetails.getId(),userDetails.getEmail(), userDetails.getDateOfBirth(), userDetails.getPhone(), userDetails.getName(),userDetails.getRoles());
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setToken(jwt);
        Map<String, Object> map = new HashMap<>();
        map.put("token", jwtAuthResponse.getToken());
        map.put("user", userResponse);
        response.setHttpStatus(HttpStatus.OK);
        response.setData(map);
        response.setMessage("Đăng nhập thành công");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
