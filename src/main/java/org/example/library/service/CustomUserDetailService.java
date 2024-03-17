package org.example.library.service;

import org.example.library.entities.Admin;
//import org.example.library.entities.Role;
import org.example.library.exceptions.ResourceNotFoundException;
import org.example.library.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private AdminRepository adminRepository;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // loading user from database by username
        Admin admin = adminRepository.findByAdminEmail(username).orElseThrow(() -> new RuntimeException("user not found"));
//
        return User.withUsername(admin.getAdminEmail())
                .password(admin.getAdminPassword())
                .build();
    }
}
