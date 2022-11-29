package com.findwork.findwork.Services;

import com.findwork.findwork.Entities.JobApplication;
import com.findwork.findwork.Entities.JobOffer;
import com.findwork.findwork.Entities.Users.UserCompany;
import com.findwork.findwork.Entities.Users.UserPerson;
import com.findwork.findwork.Enums.Category;
import com.findwork.findwork.Enums.JobLevel;
import com.findwork.findwork.Repositories.JobApplicationRepository;
import com.findwork.findwork.Repositories.JobOfferRepository;
import com.findwork.findwork.Repositories.UserCompanyRepository;
import com.findwork.findwork.Repositories.UserPersonRepository;
import com.findwork.findwork.Requests.CreateJobOfferRequest;
import com.findwork.findwork.Requests.EditJobOfferRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OfferService {
    private final JobOfferRepository jobRepo;
    private final UserCompanyRepository companyRepo;
    private final JobApplicationRepository applicationRepo;
    private final UserPersonRepository userRepo;

    public JobOffer loadOfferById(UUID id) {
        return jobRepo.findJobOfferById(id);
    }

    public JobOffer createOffer(CreateJobOfferRequest r, UserCompany company) {
        JobOffer offer = new JobOffer(r.getTitle(), r.getDescription(), r.getRequirements(), r.getNiceToHave(), r.getBenefits(), r.getLocation(), r.getSalary(), JobLevel.valueOf(r.getJobLevel()), Category.valueOf(r.getJobCategory()), company);
        jobRepo.save(offer);
        return offer;
    }

    public List<JobOffer> getAllOffers() {
        List<JobOffer> offers = jobRepo.findAll();
        return offers;
    }

    public List<JobOffer> getCompanyOffers(UUID id) throws Exception {
        UserCompany questionableCompany = companyRepo.findUserCompanyById(id);
        if (questionableCompany == null)
            throw new Exception("Such company does not exist.");
        List<JobOffer> offers = jobRepo.findJobOfferByCompany(questionableCompany);
        return offers;
    }

    public void removeOffer(UUID id) throws Exception {
        JobOffer questionableOffer = jobRepo.findJobOfferById(id);
        if (questionableOffer == null)
            throw new Exception("The job offer you are referring to is not existent.");
        jobRepo.delete(questionableOffer);
    }

    public void editOffer(UUID id, EditJobOfferRequest r) throws Exception {
        JobOffer questionableOffer = jobRepo.findJobOfferById(id);
        if (questionableOffer == null)
            throw new Exception("The job offer you are referring to is not existent.");
        if (r.getTitle() != null)
            questionableOffer.setTitle(r.getTitle());
        if (r.getDescription() != null)
            questionableOffer.setDescription(r.getDescription());
        if (r.getRequirements() != null)
            questionableOffer.setRequirements(r.getRequirements());
        if (r.getNiceToHave() != null)
            questionableOffer.setNiceToHave(r.getNiceToHave());
        if (r.getBenefits() != null)
            questionableOffer.setBenefits(r.getBenefits());
        if (r.getLocation() != null)
            questionableOffer.setLocation(r.getLocation());
        if (r.getSalary() != null)
            questionableOffer.setSalary(r.getSalary());
        if (r.getJobLevel() != null)
            questionableOffer.setJobLevel(JobLevel.valueOf(r.getJobLevel()));
        if (r.getJobCategory() != null)
            questionableOffer.setJobCategory(Category.valueOf(r.getJobCategory()));
        jobRepo.save(questionableOffer);
    }

    public void createApplication(UserPerson user, UUID offerId) throws Exception {
        JobOffer offer = jobRepo.findJobOfferById(offerId);
        JobApplication existingApplication = applicationRepo.findJobApplicationByApplicantAndOffer(user, offer);

        if (existingApplication != null)
            throw new Exception("You have already applied for this position!");

        applicationRepo.save(new JobApplication(user, offer));
    }

    public void deleteApplication(UserPerson user, UUID offerId) {
        JobOffer offer = jobRepo.findJobOfferById(offerId);
        JobApplication application = findUserApplication(user, offer);

        applicationRepo.delete(application);
    }

    public JobApplication findUserApplication(UserPerson user, JobOffer offer) {
        return applicationRepo.findJobApplicationByApplicantAndOffer(user, offer);
    }

    public void saveOffer(UserPerson user, UUID offerId) throws Exception {
        if (user.getSavedOffers().stream().anyMatch(offer -> offer.getId().equals(offerId)))
            throw new Exception("Offer already saved!");

        JobOffer offer = jobRepo.findJobOfferById(offerId);
        user.getSavedOffers().add(offer);
        userRepo.save(user);
    }

    public void unsaveOffer(UserPerson user, UUID offerId) throws Exception {
        if (user.getSavedOffers().stream().noneMatch(offer -> offer.getId().equals(offerId)))
            throw new Exception("This offer has not been saved!");

        JobOffer offerToDelete = user.getSavedOffers().stream().filter(offer -> offer.getId().equals(offerId)).findFirst().get();
        user.getSavedOffers().remove(offerToDelete);
        userRepo.save(user);
    }

    public boolean checkSaved(UserPerson user, UUID offerId) {
        return user.getSavedOffers().stream().anyMatch(offer -> offer.getId().equals(offerId));
    }
}
