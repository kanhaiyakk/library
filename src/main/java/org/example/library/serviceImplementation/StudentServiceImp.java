package org.example.library.serviceImplementation;

import org.example.library.dto.BookDto;
import org.example.library.dto.StudentDto;
import org.example.library.entities.Student;
import org.example.library.exceptions.ApiException;
import org.example.library.exceptions.ResourceNotFoundException;
import org.example.library.repositories.StudentRepository;
import org.example.library.service.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class StudentServiceImp implements StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public StudentDto createStudent(StudentDto studentDto) {
        Student student = modelMapper.map(studentDto, Student.class);
        try {
            student.setPassword(passwordEncoder.encode(student.getPassword()));
            if (studentRepository.existsByEmail(studentDto.getEmail())) {
                throw new DataIntegrityViolationException("A student with the provided email already exists.");
            }
            if (studentRepository.existsByPhoneNumber(studentDto.getPhoneNumber())) {
                throw new DataIntegrityViolationException("A student with the provided phone number already exists.");
            }
            Student savedStudent = this.studentRepository.save(student);
            return modelMapper.map(savedStudent, StudentDto.class);
        } catch (DataIntegrityViolationException ex) {
            // Exception indicates duplicate entry
            String errorMessage = ex.getMessage();
            if (errorMessage.contains("email")) {
                throw new ApiException("A student with the provided email already exists.");
            } else if (errorMessage.contains("phone number")) {
                throw new ApiException("A student with the provided phone number already exists.");
            } else {
                throw new ApiException("Student details are invalid.");
            }
        } catch (Exception e) {
            // Other exceptions
            throw new ApiException("Student details are invalid.");
        }
    }

    @Override
    public StudentDto getStudentById(Integer id) {
        Student student = this.studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("student", "studen id", id));
        System.out.println(student.getBooks());
        StudentDto map = modelMapper.map(student, StudentDto.class);
        map.setBooksDto(student.getBooks().stream().map((book)-> this.modelMapper.map(book, BookDto.class)).collect(Collectors.toList()));
        return  map;

    }


    @Override
    public List<StudentDto> getAllStudents() {
        List<Student> allStudent = this.studentRepository.findAll();
        List<StudentDto> studentsDto = allStudent.stream().map(student -> this.modelMapper.map(student, StudentDto.class)).collect(Collectors.toList());

        return studentsDto;

    }

    @Override
    public StudentDto getStudentByEmail(String email) {
        Student student = this.studentRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("email", "student email", 1));

        return modelMapper.map(student, StudentDto.class);

    }
}
