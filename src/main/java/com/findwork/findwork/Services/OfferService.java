package com.findwork.findwork.Services;

import com.findwork.findwork.Entities.JobOffer;
import com.findwork.findwork.Entities.Users.UserCompany;
import com.findwork.findwork.Repositories.JobOfferRepository;
import com.findwork.findwork.Repositories.UserCompanyRepository;
import com.findwork.findwork.Requests.CreateJobOfferRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service()
public class OfferService{
    private final JobOfferRepository jobRepo;
    private final UserCompanyRepository companyRepo;

    public  OfferService(JobOfferRepository jobRepo, UserCompanyRepository companyRepo) {
        this.jobRepo = jobRepo;
        this.companyRepo = companyRepo;
    }

    public void createOffer(CreateJobOfferRequest r) throws Exception {
        if(jobRepo.findJobOffersByTitle(r.getTitle()) != null)
            throw new Exception("A job offer with this title already exists");
        JobOffer offer = new JobOffer(r.getTitle(), r.getRequirements(),r.getLocation(), r.getSalary(),
                r.getJobLevel(), r.getJobCategory(), companyRepo.findUserCompanyByUsername(r.getCompanyUsername()));
        jobRepo.save(offer);
    }

    public List<JobOffer>  getAllOffers()
    {
        List<JobOffer> offers = jobRepo.findAll();
        return offers;
    }
    public List<JobOffer>  getCompanyOffers(String companyUsername) throws Exception
    {
        UserCompany questionableCompany = companyRepo.findUserCompanyByUsername(companyUsername);
        if(questionableCompany == null)
            throw new Exception("A company with this name does not exist.");
        List<JobOffer> offers = jobRepo.findJobOfferByCompany(questionableCompany);
        return offers;
    }
}
