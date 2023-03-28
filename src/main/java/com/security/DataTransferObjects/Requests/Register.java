package com.security.DataTransferObjects.Requests;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Register {
    private String firstname;
    private String lastname;
    private String password;
    private String email;
}
