package com.findwork.findwork.Repositories;

import com.findwork.findwork.Entities.JobOffer;
import com.findwork.findwork.Entities.Users.UserCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface JobOfferRepository extends JpaRepository<JobOffer, UUID> {
    JobOffer findJobOffersByTitle(String title);

    List<JobOffer> findJobOfferByCompany(UserCompany company);
}
