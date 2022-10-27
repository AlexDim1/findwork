package com.findwork.findwork.Entities;

import com.findwork.findwork.Entities.Users.UserPerson;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "job_applications")
public class JobApplication {
    @Id
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @ManyToOne
    private UserPerson applicant;

    @ManyToOne
    private JobOffer offer;

    private Date date;

    public JobApplication() {
    }

    public JobApplication(UserPerson applicant, JobOffer offer, Date date) {
        this.applicant = applicant;
        this.offer = offer;
        this.date = date;
    }

    public UUID getId() {
        return id;
    }

    public UserPerson getApplicant() {
        return applicant;
    }

    public void setApplicant(UserPerson applicant) {
        this.applicant = applicant;
    }

    public JobOffer getOffer() {
        return offer;
    }

    public void setOffer(JobOffer offer) {
        this.offer = offer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
