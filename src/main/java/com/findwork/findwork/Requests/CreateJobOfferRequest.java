package com.findwork.findwork.Requests;

import com.findwork.findwork.Enums.Category;
import com.findwork.findwork.Enums.JobLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateJobOfferRequest {

    private String title;

    private String requirements;

    private String location;

    private String salary;

    private String jobLevel;

    private String jobCategory;

    private UUID companyId;
}
