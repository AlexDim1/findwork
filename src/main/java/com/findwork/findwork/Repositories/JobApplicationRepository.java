package com.findwork.findwork.Repositories;

import com.findwork.findwork.Entities.JobApplication;
import com.findwork.findwork.Entities.JobOffer;
import com.findwork.findwork.Entities.Users.UserPerson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JobApplicationRepository extends JpaRepository<JobApplication, UUID> {
    JobApplication findJobApplicationByApplicantAndOffer(UserPerson applicant, JobOffer offer);

    List<JobApplication> findAllByOffer(JobOffer offer);
}
