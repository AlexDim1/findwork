package com.findwork.findwork.Requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EditCompanyRequest {
    private String email;
    private String password;
    private String name;
    private String description;
    private String employeeCount;
    private String foundingYear;
    private String address;
}
