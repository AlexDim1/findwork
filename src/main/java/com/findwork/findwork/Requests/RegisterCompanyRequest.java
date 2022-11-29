package com.findwork.findwork.Requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterCompanyRequest {
    private String name;
    private String email;
    private String password;
    private String employeeCount;
    private String foundingYear;
    private String address;
    private String description;
}
