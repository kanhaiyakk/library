package org.example.library.service;

import org.example.library.dto.AdminDto;

public interface AdminService {

    AdminDto createAdmin(AdminDto adminDto);

    AdminDto getAdmin(Integer id);
    public boolean MatchingAdminName(String adminName);
    public boolean MatchingAdminPassword(String password);

}
