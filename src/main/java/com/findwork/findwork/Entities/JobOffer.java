package com.findwork.findwork.Entities;

import com.findwork.findwork.Entities.Users.UserCompany;
import com.findwork.findwork.Entities.Users.UserPerson;
import com.findwork.findwork.Enums.JobLevel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "job_offers")
public class JobOffer {
    @Id
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private String title;

    @Column(columnDefinition = "TEXT", length = 4000)
    private String description;

    private String location;

    private String salary;

    @Enumerated
    private JobLevel jobLevel;

    @ManyToOne
    private UserCompany company;

    @OneToMany(mappedBy = "offer")
    private List<Application> applications;

    public JobOffer() {
    }

    public JobOffer(String title, String description, String location, String salary, JobLevel jobLevel) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.salary = salary;
        this.jobLevel = jobLevel;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public JobLevel getLevel() {
        return jobLevel;
    }

    public void setLevel(JobLevel jobLevel) {
        this.jobLevel = jobLevel;
    }
}
