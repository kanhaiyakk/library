package org.example.library.payload;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class JwtRequest {
    private String email;
    private  String  password;
}