package org.example.library.payload;


import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class JwtResponse {
    private String jwtToken;
    private  String  username;
}