package org.example.library.repositories;

import org.example.library.dto.StudentDto;
import org.example.library.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student,Integer>{

    Optional<Student> findByEmail(String username);
    boolean existsByPhoneNumber(String phone);
    boolean existsByEmail(String email);
      StudentDto findByRoll(Integer id);
}
