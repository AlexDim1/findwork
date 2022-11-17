package com.findwork.findwork.Services;

import com.findwork.findwork.Entities.JobOffer;
import com.findwork.findwork.Entities.Users.UserCompany;
import com.findwork.findwork.Repositories.JobOfferRepository;
import com.findwork.findwork.Repositories.UserCompanyRepository;
import com.findwork.findwork.Requests.CreateJobOfferRequest;
import com.findwork.findwork.Requests.EditCompanyRequest;
import com.findwork.findwork.Requests.EditJobOfferRequest;
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
        if(jobRepo.findJobOfferByTitle(r.getTitle()) != null)
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

    public void removeOffer(String title) throws Exception {
        JobOffer questionableOffer = jobRepo.findJobOfferByTitle(title);
        if(questionableOffer == null)
            throw new Exception("A job offer with this title does not exists");
        jobRepo.delete(questionableOffer);
    }

    public void editOffer(EditJobOfferRequest r) throws Exception {
        System.out.println(r.getOldTitle()); ////// WTF??????????????????????????????????
        JobOffer questionableOffer = jobRepo.findJobOfferByTitle(r.getOldTitle());
        if(questionableOffer == null)
            throw new Exception("The job offer you are refering to is not existent.");
        if(r.getTitle() != null)
        {
            if (jobRepo.findJobOfferByTitle(r.getTitle()) != null)
                throw new Exception("A job offer with this title already exists");
            else questionableOffer.setTitle(r.getTitle());
        }
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
