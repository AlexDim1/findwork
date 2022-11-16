package com.findwork.findwork.Requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterPersonRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;

    public RegisterPersonRequest() {
    }
}
