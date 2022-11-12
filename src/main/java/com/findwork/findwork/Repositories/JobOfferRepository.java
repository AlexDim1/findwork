package com.findwork.findwork.Repositories;

import com.findwork.findwork.Entities.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JobOfferRepository extends JpaRepository<JobOffer, UUID> {
    JobOffer findJobOffersByTitle(String title);
}
