package com.findwork.findwork.Entities;

import com.findwork.findwork.Entities.Users.UserPerson;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "job_applications")
@Getter
@Setter
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

    public JobApplication(UserPerson applicant, JobOffer offer) {
        this.applicant = applicant;
        this.offer = offer;
        this.date = Date.from(Instant.now());
    }
}
