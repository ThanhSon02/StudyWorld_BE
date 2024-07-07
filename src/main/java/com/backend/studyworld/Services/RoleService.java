package com.backend.studyworld.Services;

import com.backend.studyworld.Model.Role;
import com.backend.studyworld.Repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Role findById(int roleId) {
        return roleRepository.findById(roleId).get();
    }
}
