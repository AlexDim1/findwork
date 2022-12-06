package com.findwork.findwork.Repositories;

import com.findwork.findwork.Entities.JobOffer;
import com.findwork.findwork.Entities.UserSavedOffer;
import com.findwork.findwork.Entities.Users.UserPerson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;
import java.util.UUID;

public interface UserSavedOfferRepository extends JpaRepository<UserSavedOffer, UUID> {
    Set<UserSavedOffer> findAllByUser(UserPerson user);

    UserSavedOffer findUserSavedOfferById(UUID id);

    UserSavedOffer findUserSavedOfferByUserAndOffer(UserPerson user, JobOffer offer);
}
