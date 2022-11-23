package com.findwork.findwork.Services;

import com.findwork.findwork.Entities.JobOffer;
import com.findwork.findwork.Entities.Users.UserCompany;
import com.findwork.findwork.Repositories.JobOfferRepository;
import com.findwork.findwork.Repositories.UserCompanyRepository;
import com.findwork.findwork.Requests.CreateJobOfferRequest;
import com.findwork.findwork.Requests.EditJobOfferRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service()
public class OfferService{
    private final JobOfferRepository jobRepo;
    private final UserCompanyRepository companyRepo;

    public  OfferService(JobOfferRepository jobRepo, UserCompanyRepository companyRepo) {
        this.jobRepo = jobRepo;
        this.companyRepo = companyRepo;
    }

    public void createOffer(CreateJobOfferRequest r){
        JobOffer offer = new JobOffer(r.getTitle(), r.getRequirements(),r.getLocation(), r.getSalary(),
                r.getJobLevel(), r.getJobCategory(), companyRepo.findUserCompanyById(r.getCompanyId()));
        jobRepo.save(offer);
    }

    public List<JobOffer>  getAllOffers()
    {
        List<JobOffer> offers = jobRepo.findAll();
        return offers;
    }
    public List<JobOffer>  getCompanyOffers(UUID id) throws Exception
    {
        UserCompany questionableCompany = companyRepo.findUserCompanyById(id);
        if(questionableCompany == null)
            throw new Exception("Such company does not exist.");
        List<JobOffer> offers = jobRepo.findJobOfferByCompany(questionableCompany);
        return offers;
    }

    public void removeOffer(UUID id) throws Exception {
        JobOffer questionableOffer = jobRepo.findJobOfferById(id);
        if(questionableOffer == null)
            throw new Exception("The job offer you are referring to is not existent.");
        jobRepo.delete(questionableOffer);
    }

    public void editOffer(UUID id, EditJobOfferRequest r) throws Exception {
        JobOffer questionableOffer = jobRepo.findJobOfferById(id);
        if(questionableOffer == null)
            throw new Exception("The job offer you are referring to is not existent.");
        if(r.getTitle() != null)
            questionableOffer.setTitle(r.getTitle());
        if(r.getDescription() != null)
            questionableOffer.setDescription(r.getDescription());
        if(r.getRequirements() != null)
            questionableOffer.setRequirements(r.getRequirements());
        if(r.getNiceToHave() != null)
            questionableOffer.setNiceToHave(r.getNiceToHave());
        if(r.getBenefits() != null)
            questionableOffer.setBenefits(r.getBenefits());
        if(r.getLocation() != null)
            questionableOffer.setLocation(r.getLocation());
        if(r.getSalary() != null)
            questionableOffer.setSalary(r.getSalary());
        if(r.getJobLevel() != null)
            questionableOffer.setJobLevel(r.getJobLevel());
        if(r.getJobCategory() != null)
            questionableOffer.setJobCategory(r.getJobCategory());
        jobRepo.save(questionableOffer);
    }
}
