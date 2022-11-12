package com.findwork.findwork.Requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EditPersonRequest {
    private String oldEmail;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private int age;

    private String education;

    private String skills;
}
