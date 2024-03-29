package org.example.library.service;

import org.example.library.dto.StudentDto;
import org.example.library.payload.OtpResponse;

public interface OtpService {

  OtpResponse generateOtpPin(String phonenumber);
}
