package org.example.library.service;

import org.example.library.dto.AdminDto;
import org.example.library.dto.BookDto;
import org.example.library.dto.StudentDto;

public interface AdminService {

    AdminDto createAdmin(AdminDto adminDto);

    AdminDto getAdmin(Integer id);
    public boolean MatchingAdminName(String adminName);
    public boolean MatchingAdminPassword(String password);

    StudentDto issueBook(Integer id, BookDto bookDto);

    StudentDto getStudentDetails(Integer roll);

    StudentDto submitBook(Integer bookId,Integer roll);

}
