package com.backend.studyworld.DTO.response;

import com.backend.studyworld.Model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRes {
    private int id;
    private String email;
    private Date dateOfBirth;
    private String phone;
    private String name;
    private Set<Role> roles = new HashSet<>();
}
