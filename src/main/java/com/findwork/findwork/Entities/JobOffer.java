package com.findwork.findwork.Entities;

import com.findwork.findwork.Entities.Users.UserCompany;
import com.findwork.findwork.Enums.Category;
import com.findwork.findwork.Enums.JobLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "job_offers")
@Getter
@Setter
public class JobOffer {
    @Id
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private String title;

    @Column(columnDefinition = "TEXT", length = 4000)
    private String description;

    @Column(columnDefinition = "TEXT", length = 2000)
    private String requirements;

    @Column(columnDefinition = "TEXT", length = 2000)
    private String niceToHave;

    @Column(columnDefinition = "TEXT", length = 2000)
    private String benefits;

    private String location;

    private String salary;

    private int viewCount;

    @Enumerated
    private JobLevel jobLevel;

    @Enumerated
    private Category jobCategory;

    @ManyToOne
    private UserCompany company;

    @OneToMany(mappedBy = "offer")
    private List<JobApplication> jobApplications;

    public JobOffer() {
    }

    public JobOffer(String title, String requirements, String location, String salary, JobLevel jobLevel, Category jobCategory, UserCompany company) {
        this.title = title;
        this.requirements = requirements;
        this.location = location;
        this.salary = salary;
        this.jobLevel = jobLevel;
        this.jobCategory = jobCategory;
        this.company = company;
    }
}
