package org.example.library.serviceImplementation;

import org.example.library.dto.AdminDto;
import org.example.library.dto.BookDto;
import org.example.library.dto.StudentDto;
import org.example.library.entities.Admin;
import org.example.library.entities.Book;
import org.example.library.entities.Student;
import org.example.library.exceptions.ApiException;
import org.example.library.exceptions.ResourceNotFoundException;
import org.example.library.repositories.AdminRepository;
import org.example.library.repositories.BookRepository;
import org.example.library.repositories.StudentRepository;
import org.example.library.service.AdminService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImp implements AdminService {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AdminRepository adminRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private BookRepository bookRepository;
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

    @Override
    public StudentDto issueBook(Integer id,BookDto bookDto) {
        try{
            Student student = this.studentRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("student not found ","student roll",id));
            Book book = modelMapper.map(bookDto, Book.class);
            if(this.bookRepository.existsByBookId(book.getBookId())){
                Book book1 = this.bookRepository.findById(book.getBookId()).orElseThrow(() -> new ResourceNotFoundException("book", "bookId", book.getBookId()));
                if(book1.getDateOfIssue()!=null){
                    throw  new ApiException("Book is already assigned");
                }else{
                    book1.setDateOfIssue(LocalDate.now());
                    book1.setStudent(student);
                    Book save = this.bookRepository.save(book1);
                    return modelMapper.map(student,StudentDto.class);
                }

            }else{
                book.setStudent(student);
                book.setDateOfIssue(LocalDate.now());
                Book save = this.bookRepository.save(book);
                return modelMapper.map(student,StudentDto.class);

            }

        }catch (Exception e){
            throw  new ApiException(e.getMessage());
        }


    }
}
