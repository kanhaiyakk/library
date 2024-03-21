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
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

            Student student = this.studentRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("student not found ","student roll",id));
            Book book = modelMapper.map(bookDto, Book.class);
            if(this.bookRepository.existsByBookId(book.getBookId())){
                Book book1 = this.bookRepository.findById(book.getBookId()).orElseThrow(() -> new ResourceNotFoundException("book", "bookId", book.getBookId()));
                if(book1.getDateOfIssue()!=null && book1.getDateOfSubmission()==null){
                    throw  new ApiException("Book is already assigned to  roll no "+ book1.getStudent().getRoll());
                }else{
                    book1.setDateOfIssue(LocalDate.now());
                    book1.setStudent(student);
                    book1.setDateOfSubmission(null);
                    Book save = this.bookRepository.save(book1);
                    return modelMapper.map(student,StudentDto.class);
                }

            }else{

                book.setStudent(student);
                book.setDateOfIssue(LocalDate.now());
                Book save = this.bookRepository.save(book);
                return modelMapper.map(student,StudentDto.class);

            }

    }

    @Override
    public StudentDto getStudentDetails(Integer roll) {
        Student student = this.studentRepository.findById(roll)
                .orElseThrow(() -> new ResourceNotFoundException("student ", "student id", roll));

        return modelMapper.map(student,StudentDto.class);



    }

    @Override
    public StudentDto submitBook(Integer bookId,Integer roll) {
        Book book = this.bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("book", "bookId", bookId));

        Student student = this.studentRepository.findById(roll).orElseThrow(() -> new ResourceNotFoundException("student", "studendId", roll));
        try{
            Student student1 = book.getStudent();

            if(Objects.equals(student1.getRoll(), student.getRoll())){
                if(book.getDateOfSubmission()!=null){
                    throw  new ApiException("book is already submitted");
                }else{
                    book.setDateOfSubmission(LocalDate.now());
                    Book book1 = this.bookRepository.save(book);
                }

            }else{
                throw  new Exception("this book is not associated with this student");
            }

        }catch (Exception e){
            throw new ApiException("error in submitting book. "+e.getMessage());
        }
        return modelMapper.map(student,StudentDto.class);

    }

    @Override
    public List<AdminDto> getAllAdmins() {
        List<AdminDto> adminDto = this.adminRepository.findAll()
                .stream().map((admin) -> this.modelMapper.map(admin, AdminDto.class)).collect(Collectors.toList());
        return adminDto;
    }
}
