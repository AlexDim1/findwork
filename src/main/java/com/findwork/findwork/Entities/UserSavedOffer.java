package com.findwork.findwork.Entities;

import com.findwork.findwork.Entities.Users.UserPerson;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserSavedOffer {
    @Id
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @ManyToOne
    private UserPerson user;

    @ManyToOne
    private JobOffer offer;

    public UserSavedOffer(UserPerson user, JobOffer offer) {
        this.user = user;
        this.offer = offer;
    }
}
