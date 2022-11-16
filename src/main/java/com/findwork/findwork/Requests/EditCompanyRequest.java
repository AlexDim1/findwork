package com.findwork.findwork.Requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EditCompanyRequest {
    private String oldEmail;

    private String email;

    private String password;

    private String name;

    private String description;

    private int employeeCount;

    private int foundingYear;

    private String address;

    public EditCompanyRequest() {
    }
}
