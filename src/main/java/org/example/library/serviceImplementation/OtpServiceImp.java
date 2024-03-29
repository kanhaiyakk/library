package org.example.library.serviceImplementation;

import org.example.library.dto.StudentDto;
import org.example.library.entities.Role;
import org.example.library.entities.Student;
import org.example.library.exceptions.ApiException;
import org.example.library.exceptions.ResourceNotFoundException;
import org.example.library.payload.OtpResponse;
import org.example.library.repositories.RoleRepository;
import org.example.library.repositories.StudentRepository;
import org.example.library.service.OtpService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.Set;

@Service
public class OtpServiceImp implements OtpService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public OtpResponse generateOtpPin(String email) {

        Student student = this.studentRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("user", email, 0));
        Set<Role> roles = student.getRoles();
//        Object o = this.studentRepository.findById(roles).get();
//        Integer roll = student.getRoll();
//        System.out.println(roll);
//        System.out.println(roles);

        StudentDto studentDto = this.modelMapper.map(student, StudentDto.class);
        String otp = generateRandomOtp();
        System.out.println(otp);
        LocalDateTime creationTime = LocalDateTime.now();
        return  new OtpResponse(otp,creationTime,studentDto);
    }

    private String generateRandomOtp() {
        Random random = new Random();
        int otpNumber = random.nextInt(900000) + 100000; // Generates a random number between 100000 and 999999
        return String.valueOf(otpNumber);
    }

    private boolean isOtpExpired(String email, LocalDateTime creationTime) {
        // Get the current time
        LocalDateTime currentTime = LocalDateTime.now();

        // Calculate the difference in minutes between current time and creation time of OTP
        long minutesElapsed = creationTime.until(currentTime, java.time.temporal.ChronoUnit.MINUTES);

        // Check if the difference exceeds 5 minutes
        System.out.println(minutesElapsed);
        return minutesElapsed >= 1;
    }
}
