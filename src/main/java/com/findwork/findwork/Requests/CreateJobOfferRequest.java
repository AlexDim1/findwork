package com.findwork.findwork.Requests;

import com.findwork.findwork.Entities.Users.UserCompany;
import com.findwork.findwork.Enums.Category;
import com.findwork.findwork.Enums.JobLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateJobOfferRequest {

    private String title;

    private String requirements;

    private String location;

    private String salary;

    private JobLevel jobLevel;

    private Category jobCategory;

    private UserCompany company;

    public CreateJobOfferRequest() {
    }

    public String getCompanyUsername() {return company.getUsername();}
}
