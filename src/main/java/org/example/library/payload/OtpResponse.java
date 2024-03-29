package org.example.library.payload;

import lombok.*;
import org.example.library.dto.StudentDto;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OtpResponse {

    String otp;
    LocalDateTime localDateTime;
    StudentDto studentDto;
}
