package org.example.library.controllers;

import org.example.library.exceptions.ApiException;
import org.example.library.payload.ApiResponse;
import org.example.library.payload.JwtRequest;
import org.example.library.payload.JwtResponse;
import org.example.library.security.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/student")
public class StudentAuthController {


    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager manager;


    @Autowired
    private JwtHelper helper;


    @PostMapping("/log")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {

        this.doAuthenticate(request.getEmail(), request.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        System.out.println(userDetails);
        String token = this.helper.generateToken(userDetails);

        JwtResponse response = JwtResponse.builder()
                .jwtToken(token)
                .username(userDetails.getUsername()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    private void doAuthenticate(String email, String password) {


        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        System.out.println(authentication+"authen");

        try {
            manager.authenticate(authentication);


        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }

    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse> exceptionHandler() {

        ApiResponse apiResponse = new ApiResponse("Invalid Credentials", false);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
}

