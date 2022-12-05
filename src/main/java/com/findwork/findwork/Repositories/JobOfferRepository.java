package com.findwork.findwork.Repositories;

import com.findwork.findwork.Entities.JobOffer;
import com.findwork.findwork.Entities.Users.UserCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface JobOfferRepository extends JpaRepository<JobOffer, UUID> {

    JobOffer findJobOfferById(UUID id);

    List<JobOffer> findJobOfferByCompany(UserCompany company);

    @Query("select j from JobOffer j where lower(j.benefits) like lower(concat('%', ?1, '%'))")
    List<JobOffer> findJobOffersByBenefitsContaining(String benefits);

    @Query("select j from JobOffer j where lower(j.description) like lower(concat('%', ?1, '%'))")
    List<JobOffer> findJobOffersByDescriptionContaining(String description);

    @Query("select j from JobOffer j where concat(j.jobCategory,'')  like %?1%")
    List<JobOffer> findJobOffersByJobCategoryContaining(String jobCategory); // jobCategory се въвежда като число, т.е: 1 --> Commerce

    @Query("select j from JobOffer j where concat(j.jobLevel,'')  like %?1%")
    List<JobOffer> findJobOffersByJobLevelContaining(String jobLevel); // jobLevel се въвежда като число, т.е: 1 --> Junior

    @Query("select j from JobOffer j where lower(j.location) like lower(concat('%', ?1, '%'))")
    List<JobOffer> findJobOffersByLocationContaining(String location);

    @Query("select j from JobOffer j where lower(j.niceToHave) like lower(concat('%', ?1, '%'))")
    List<JobOffer> findJobOffersByNiceToHaveContaining(String niceToHave);

    @Query("select j from JobOffer j where lower(j.requirements) like lower(concat('%', ?1, '%'))")
    List<JobOffer> findJobOffersByRequirementsContaining(String requirements);

    @Query("select j from JobOffer j where CAST(j.salary AS int) >= CAST(?1 AS int) AND CAST(j.salary AS int) <= CAST(?2 AS int)")
    List<JobOffer> findJobOffersBySalary(String lowEnd, String highEnd);

    @Query("select j from JobOffer j where lower(j.title) like ?1 or lower(j.requirements) like ?1 or lower(j.description) like ?1")
    List<JobOffer> findJobOffersByKeyWords(String keyWord);


}
