package com.backend.studyworld.Services;


import com.backend.studyworld.DTO.response.UserRes;
import com.backend.studyworld.Model.User;
import com.backend.studyworld.Repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private ModelMapper modelMapper;
    public UserService() {
        this.modelMapper = new ModelMapper();
    }

    public User findUserByEmail(String email) {
        try {
            return userRepository.findUserByEmail(email).get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Boolean existsUserByEmail(String email) {
        return userRepository.existsUserByEmail(email);
    }

    public UserRes registerUser(UserRes userDto, int userId) {
        User user = this.modelMapper.map(userDto, User.class);
        User userSaved = userRepository.save(user);
        UserRes dto = this.modelMapper.map(userSaved, UserRes.class);
        return dto;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public boolean changPassword(String email, String oldPassword, String newPassword) {
        try {
            User user = findUserByEmail(email);
            boolean checkPassword = passwordEncoder.matches(oldPassword, user.getPassword());
            if(checkPassword) {
                user.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(user);
                return true;
            } else {
                return false;
            }
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkPass(String email, String password) {
        try {
            User user = userRepository.findUserByEmail(email).get();
            return passwordEncoder.matches(password, user.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<UserRes> getAllUser() {
        try {
            List<User> list = userRepository.findAll();
            if(list.size() > 0) {
                List<UserRes> listDto = list.stream().map(user -> modelMapper.map(user, UserRes.class)).collect(Collectors.toList());
                return listDto;
            } else {
                return null;
            }
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean delete(int id) {
        try {
            userRepository.deleteById(id);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
