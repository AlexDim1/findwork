package com.findwork.findwork.Entities;

import com.findwork.findwork.Entities.Users.UserCompany;
import com.findwork.findwork.Enums.Category;
import com.findwork.findwork.Enums.JobLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "job_offers")
@Getter
@Setter
@NoArgsConstructor
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

    @Enumerated
    private JobLevel jobLevel;

    @Enumerated
    private Category jobCategory;

    @ManyToOne(cascade = {CascadeType.REFRESH})
    private UserCompany company;

    @OneToMany(mappedBy = "offer", cascade = CascadeType.REMOVE)
    private List<JobApplication> jobApplications;

    @OneToMany(mappedBy = "offer", cascade = CascadeType.REMOVE)
    private List<UserSavedOffer> saves;

    public JobOffer(String title, String description, String requirements, String niceToHave, String benefits, String location, String salary, JobLevel jobLevel, Category jobCategory, UserCompany company) {
        this.title = title;
        this.description = description;
        this.requirements = requirements;
        this.niceToHave = niceToHave;
        this.benefits = benefits;
        this.location = location;
        this.salary = salary;
        this.jobLevel = jobLevel;
        this.jobCategory = jobCategory;
        this.company = company;
    }
}
