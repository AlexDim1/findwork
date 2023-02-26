package com.findwork.findwork.Repositories;

import com.findwork.findwork.Entities.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JobOfferRepository extends JpaRepository<JobOffer, UUID> {

    JobOffer findJobOfferById(UUID id);
    JobOffer findFirstByTitleStartsWith(String title);
    List<JobOffer> findJobOffersByTitleStartsWith(String title);
}
