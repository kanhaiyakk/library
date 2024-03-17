package org.example.library.controllers;

import jakarta.validation.Valid;
import org.example.library.dto.AdminDto;
import org.example.library.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @PostMapping("/")
    public ResponseEntity<AdminDto> createAdmin(@Valid @RequestBody AdminDto adminDto) {
        AdminDto createAdminDto = adminService.createAdmin(adminDto);
        return new ResponseEntity<>(createAdminDto, HttpStatus.CREATED);

    }

    @GetMapping("/{adminId}")
    public ResponseEntity<AdminDto> getAdmin(@PathVariable("adminId") Integer adminId) {
        AdminDto getAdmin = this.adminService.getAdmin(adminId);
        return new ResponseEntity<>(getAdmin, HttpStatus.OK);

    }
}