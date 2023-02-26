package com.findwork.findwork.Controllers;

import com.findwork.findwork.Entities.JobOffer;
import com.findwork.findwork.Entities.Users.UserCompany;
import com.findwork.findwork.Entities.Users.UserPerson;
import com.findwork.findwork.Enums.Category;
import com.findwork.findwork.Enums.JobLevel;
import com.findwork.findwork.Repositories.JobOfferRepository;
import com.findwork.findwork.Repositories.UserCompanyRepository;
import com.findwork.findwork.Repositories.UserPersonRepository;
import com.findwork.findwork.Requests.CreateJobOfferRequest;
import com.findwork.findwork.Services.OfferService;
import com.findwork.findwork.Services.UserService;
import com.findwork.findwork.Services.ValidationService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@SpringBootTest
class JobOfferControllerTest {
    @Autowired
    AuthenticationManager am;
    @Autowired
    ValidationService vs;
    @Autowired
    UserService us;
    @Autowired
    OfferService os;
    @Autowired
    UserCompanyRepository companyRepo;
    @Autowired
    JobOfferRepository jobRepo;
    @Autowired
    UserPersonRepository userRepo;

    private Random rand = new Random();


    @Test
    void createOffer() {
        // Mock up HttpSession and insert it into mocked up HttpServletRequest
        HttpSession session = mock(HttpSession.class);
        given(session.getId()).willReturn("sessionid");
        // Mock up HttpServletRequest
        HttpServletRequest request = mock(HttpServletRequest.class);
        given(request.getSession()).willReturn(session);
        given(request.getSession(true)).willReturn(session);
        HashMap<String, String[]> params = new HashMap<>();
        given(request.getParameterMap()).willReturn(params);
        Model model = Mockito.mock(Model.class);
        RedirectAttributes flashAttributes = Mockito.mock(RedirectAttributes.class);
        //AuthenticationController ac = new AuthenticationController(us, vs, am);
        JobOfferController joc = new JobOfferController(vs, os);

        List<UserCompany> testCompanies = companyRepo.findUserCompaniesByUsernameStartsWith("TestCompany");
        for (int i = 1; i <= 10; i++) {
            int randomCompanyNumber = rand.nextInt(testCompanies.size()) + 1;
            UserCompany selectedCompany = companyRepo.findUserCompanyByName("TestCompany" + randomCompanyNumber);

            CreateJobOfferRequest jobOfferRequest = new CreateJobOfferRequest();
            jobOfferRequest.setTitle("Example job offer owned by TestCompany" + randomCompanyNumber);
            jobOfferRequest.setRequirements("Basic understanding of programming or ability to carry heavy weights from point A to point B.");
            jobOfferRequest.setNiceToHave("Nice to have a finished college degree and cooperative skills.");
            jobOfferRequest.setDescription("This is an example job offer, created for testing purposes.");
            jobOfferRequest.setBenefits("One welcome drink at the office lobby every morning(all day free drinks if celebrating birthday)!");
            jobOfferRequest.setLocation("Wall Street");
            jobOfferRequest.setSalary(String.valueOf(rand.nextInt(2000) + 100));
            jobOfferRequest.setJobLevel(Arrays.stream(JobLevel.values()).toArray()[rand.nextInt(JobLevel.values().length)].toString());
            jobOfferRequest.setJobCategory(Arrays.stream(Category.values()).toArray()[rand.nextInt(Category.values().length)].toString());


            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(selectedCompany.getUsername(), "12345678");
            authToken.setDetails(new WebAuthenticationDetails(request));
            joc.createOffer(am.authenticate(authToken), jobOfferRequest, flashAttributes);
        }
    }


    @Disabled
    @Test
    void saveOfferAndApply()
    {
        List<UserPerson> testPeople = userRepo.findUserPeopleByUsernameStartsWith("TestUser");
        List<JobOffer> testJobOffers = jobRepo.findJobOffersByTitleStartsWith("Example job offer owned by TestCompany");
        for(int i = 1; i <= 17; i++)
        {
            try
            {
                int randomPersonNumber = rand.nextInt(testPeople.size()) + 1;
                UserPerson selectedPerson = userRepo.findUserPersonByUsername("TestUser" + randomPersonNumber + "@gmail.com");

                int randomJobOfferNumber = rand.nextInt(testJobOffers.size());
                JobOffer selectedOffer = testJobOffers.get(randomJobOfferNumber);

                os.createApplication(selectedPerson, selectedOffer.getId());
                os.saveOffer(selectedPerson, selectedOffer.getId());
            }
            catch (Exception e)
            {
                System.out.println("Already existing record. Skipping. :)");
            }
        }
    }

    @Disabled
    @Test
    void removeTestOffers() throws Exception {
        while (jobRepo.findFirstByTitleStartsWith("Example job offer owned by TestCompany") != null) {
            os.removeOffer(jobRepo.findFirstByTitleStartsWith("Example job offer owned by TestCompany").getId());
        }
    }

    @Disabled
    @Test
    void deleteTestSavesAndApplications()
    {
        List<UserPerson> testPeople = userRepo.findUserPeopleByUsernameStartsWith("TestUser");
        for(UserPerson p : testPeople)
        {
            os.testUsersDeleteSavedAndApplied(p);
        }
    }
}