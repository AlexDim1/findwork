package com.findwork.findwork.Entities;

import com.findwork.findwork.Entities.Users.UserPerson;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
public class JobApplication {
    @Id
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @ManyToOne
    private UserPerson user;

    @ManyToOne
    private JobOffer offer;

    private Date date;

    public JobApplication(UserPerson user, JobOffer offer) {
        this.user = user;
        this.offer = offer;
        this.date = Date.from(Instant.now());
    }
}
