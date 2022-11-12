package com.findwork.findwork.Services;

import com.findwork.findwork.Entities.JobOffer;
import com.findwork.findwork.Entities.Users.UserPerson;
import com.findwork.findwork.Repositories.JobOfferRepository;
import com.findwork.findwork.Requests.CreateJobOfferRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("os")
public class OfferService{
    private final JobOfferRepository jobRepo;

    public  OfferService(JobOfferRepository jobRepo) {
        this.jobRepo = jobRepo;
    }

    public void createOffer(CreateJobOfferRequest r) throws Exception {
        if(jobRepo.findJobOffersByTitle(r.getTitle()) != null)
            throw new Exception("A job offer with this title already exists");
        JobOffer offer = new JobOffer(r.getTitle(), r.getRequirements(),r.getLocation(), r.getSalary(), r.getJobLevel(), r.getJobCategory(), r.getCompany());
        jobRepo.save(offer);
    }

}
