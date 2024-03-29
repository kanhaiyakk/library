package org.example.library.controllers;

import org.example.library.payload.ApiResponse;
import org.example.library.payload.OtpResponse;
import org.example.library.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/otp")
public class OtpController {



    @Autowired
    private OtpService otpService;

    @GetMapping("/pin")
    public ResponseEntity<OtpResponse> generateOtp(@RequestParam("email") String email){
        OtpResponse otpResponse = this.otpService.generateOtpPin(email);
        return new ResponseEntity<>(otpResponse, HttpStatus.OK);
    }
}
