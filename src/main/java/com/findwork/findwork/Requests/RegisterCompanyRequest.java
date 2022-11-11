package com.findwork.findwork.Requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterCompanyRequest {
    private String name;
    private String email;
    private String password;
}
