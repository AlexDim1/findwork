package com.findwork.findwork.Controllers;

import com.findwork.findwork.Entities.Users.UserPerson;
import com.findwork.findwork.Repositories.UserCompanyRepository;
import com.findwork.findwork.Repositories.UserPersonRepository;
import com.findwork.findwork.Requests.EditPersonRequest;
import com.findwork.findwork.Requests.RegisterCompanyRequest;
import com.findwork.findwork.Requests.RegisterPersonRequest;
import com.findwork.findwork.Services.UserService;
import com.findwork.findwork.Services.ValidationService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@SpringBootTest
@DisplayName("User Registration Controller")
class AuthenticationControllerTest {
    @Autowired
    ValidationService vs;
    @Autowired
    UserService us;
    @Autowired
    AuthenticationManager am;
    @Autowired
    UserPersonRepository userRepo;
    @Autowired
    UserCompanyRepository companyRepo;

    private String[] skills = {"Karucar","Hlebar","Bezdelnik","Moshenik","Igrach","Piqnica","Narkoman","Pisar","Muzikant","Hudojnik","Rabota s Excel",
            "Programist","Bodibildur","Montajnik","Vodoprovodchik","Gangster","Tiradjiq", "Lepi mazilka", "Kurti", "Hamalin", "Ribar", "Jenkar"};
    private String[] educations = {"Sredno profesionalno", "Sredno", "Vishe", "Bez(ot malcinstvoto)", "Bakalavur", "Magistur", "Profesor", "Osnovno"};

    private Random rand = new Random();

    @Test
    void registerAndEditPerson() throws IOException //doesnt rewrite existing data
    {
        // Mock up HttpSession and insert it into mocked up HttpServletRequest
        HttpSession session = mock(HttpSession.class);
        given(session.getId()).willReturn("sessionid");
        // Mock up HttpServletRequest
        HttpServletRequest request = mock(HttpServletRequest.class);
        given(request.getSession()).willReturn(session);
        given(request.getSession(true)).willReturn(session);
        HashMap<String,String[]> params = new HashMap<>();
        given(request.getParameterMap()).willReturn(params);

        Model model = Mockito.mock(Model.class);

        AuthenticationController ac = new AuthenticationController(us, vs, am);
        UserController uc = new UserController(us,vs);
        for(int i = 1; i<= 7; i++)
        {
            RegisterPersonRequest personRequest = new RegisterPersonRequest();
            personRequest.setEmail("TestUser"+ i +"@gmail.com");
            personRequest.setPassword("12345678");
            personRequest.setFirstName("TestUser");
            personRequest.setLastName(Integer.toString(i));
            personRequest.setBirthDate(LocalDate.parse("1999-12-27"));

            ac.registerPerson(personRequest, model, request);
            UserPerson registeredPerson = userRepo.findUserPersonByUsername(personRequest.getEmail());
            assertNotNull(registeredPerson);
            //-----------------------------EditPerson---------------------------------

            EditPersonRequest editRequest = new EditPersonRequest();
            if(registeredPerson.getSkills() == null)
            {
                int numberOfSkills = rand.nextInt(6) + 1;
                String personSkills[] = new String[numberOfSkills];
                for (int j = 0; j < numberOfSkills; j++) {
                    personSkills[j] = skills[rand.nextInt(skills.length)];
                }
                String[] distinctSkillsArray = new HashSet<String>(Arrays.asList(personSkills)).toArray(new String[0]); //Remove duplicates
                String distinctSkills = Arrays.toString(distinctSkillsArray);
                editRequest.setSkills(distinctSkills.substring(1, distinctSkills.length() - 1));
            }
            if(registeredPerson.getEducation() == null)
            {
                editRequest.setEducation(educations[rand.nextInt(educations.length)]);
            }

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(personRequest.getEmail(), personRequest.getPassword());
            authToken.setDetails(new WebAuthenticationDetails(request));
            uc.editPerson(registeredPerson.getId(), editRequest, model, am.authenticate(authToken));
        }

    }

    @Test
    void registerCompany() throws Exception //doesnt rewrite existing data
    {
        // Mock up HttpSession and insert it into mocked up HttpServletRequest
        HttpSession session = mock(HttpSession.class);
        given(session.getId()).willReturn("sessionid");
        // Mock up HttpServletRequest
        HttpServletRequest request = mock(HttpServletRequest.class);
        given(request.getSession()).willReturn(session);
        given(request.getSession(true)).willReturn(session);
        HashMap<String,String[]> params = new HashMap<>();
        given(request.getParameterMap()).willReturn(params);

        Model model = Mockito.mock(Model.class);

        AuthenticationController ac = new AuthenticationController(us, vs, am);
        for(int i = 1; i<= 7; i++)
        {
            RegisterCompanyRequest companyRequest = new RegisterCompanyRequest();
            companyRequest.setName("TestCompany" + i);
            companyRequest.setEmail("TestCompany"+ i +"@gmail.com");
            companyRequest.setPassword("12345678");
            companyRequest.setEmployeeCount("18");
            companyRequest.setFoundingYear("1900");
            companyRequest.setAddress("Wall Street");
            companyRequest.setDescription("This is an autogenerated company. It is created for testing purposes.");

            ac.registerCompany(companyRequest, model, request);
            assertNotNull(companyRepo.findUserCompanyByUsername(companyRequest.getEmail()));
        }
    }
    @Disabled
    @Test
    void deleteAccount()
    {
        us.adminDeleteAccount("TestCompany4@gmail.com");
    }
}