package com.findwork.findwork.Requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateJobOfferRequest {
    private String title;
    private String requirements;
    private String niceToHave;
    private String description;
    private String benefits;
    private String location;
    private String salary;
    private String jobLevel;
    private String jobCategory;
}
