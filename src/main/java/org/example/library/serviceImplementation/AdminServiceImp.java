package org.example.library.serviceImplementation;

import org.example.library.dto.AdminDto;
import org.example.library.entities.Admin;
import org.example.library.exceptions.ApiException;
import org.example.library.exceptions.ResourceNotFoundException;
import org.example.library.repositories.AdminRepository;
import org.example.library.service.AdminService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminServiceImp implements AdminService {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AdminRepository adminRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public AdminDto createAdmin(AdminDto adminDto)   {
       try{
           Admin admin = modelMapper.map(adminDto, Admin.class);
           admin.setAdminPassword(passwordEncoder.encode(admin.getAdminPassword()));
           Admin saveAmin = adminRepository.save(admin);
           return modelMapper.map(saveAmin,AdminDto.class);

       }catch (Exception e){
           throw  new ApiException("Admin is already exits");
       }

    }

    @Override
    public AdminDto getAdmin(Integer id) {
            Admin admin= this.adminRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("user", "id", id));
            return modelMapper.map(admin,AdminDto.class);
    }

    @Override
    public boolean MatchingAdminName(String adminName) {
        return false;
    }

    @Override
    public boolean MatchingAdminPassword(String password) {
        return false;
    }
}
