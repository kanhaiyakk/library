package org.example.library.service;

import org.example.library.dto.StudentDto;

import java.util.List;

public interface StudentService {

    public StudentDto createStudent(StudentDto studentDto);

    StudentDto getStudentById(Integer id);

    List<StudentDto> getAllStudents();

    StudentDto getStudentByEmail(String email);
}
