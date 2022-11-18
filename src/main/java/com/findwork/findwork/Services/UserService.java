package com.findwork.findwork.Services;

import com.findwork.findwork.Entities.Users.UserCompany;
import com.findwork.findwork.Entities.Users.UserPerson;
import com.findwork.findwork.Repositories.UserCompanyRepository;
import com.findwork.findwork.Repositories.UserPersonRepository;
import com.findwork.findwork.Requests.EditCompanyRequest;
import com.findwork.findwork.Requests.EditPersonRequest;
import com.findwork.findwork.Requests.RegisterCompanyRequest;
import com.findwork.findwork.Requests.RegisterPersonRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserCompanyRepository companyRepo;
    private final UserPersonRepository personRepo;
    private final BCryptPasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserPerson foundPerson = personRepo.findUserPersonByUsername(username);
        UserCompany foundCompany = companyRepo.findUserCompanyByUsername(username);

        if (foundCompany == null && foundPerson == null) {
            throw new UsernameNotFoundException("No user found");
        }

        if (foundPerson != null)
            return foundPerson;

        return foundCompany;
    }

    public void registerPerson(RegisterPersonRequest r) throws Exception {
        if (personRepo.findUserPersonByUsername(r.getEmail()) != null
                || companyRepo.findUserCompanyByUsername(r.getEmail()) != null)
            throw new Exception(String.format("An account with the email address %s already exists", r.getEmail()));
        UserPerson registered = new UserPerson(r.getEmail(), encoder.encode(r.getPassword()), r.getFirstName(), r.getLastName());
        personRepo.save(registered);
    }

    public void registerCompany(RegisterCompanyRequest r) throws Exception {
        if (personRepo.findUserPersonByUsername(r.getEmail()) != null
                || companyRepo.findUserCompanyByUsername(r.getEmail()) != null)
            throw new Exception("An account with this email already exists");
        if(companyRepo.findUserCompanyByName(r.getName()) != null)
            throw new Exception("A company with this name already exists");
        UserCompany registered = new UserCompany(r.getEmail(), encoder.encode(r.getPassword()), r.getName());
        companyRepo.save(registered);
    }

    public void editPerson(EditPersonRequest r) throws Exception {
        UserPerson questionablePerson = personRepo.findUserPersonByUsername(r.getOldEmail());
        if(r.getEmail() != null)
        {
            if(personRepo.findUserPersonByUsername(r.getEmail()) != null
                    || companyRepo.findUserCompanyByUsername(r.getEmail()) != null)
                throw new Exception("An account with this email already exists");
            else questionablePerson.setUsername(r.getEmail());
        }
        if(r.getPassword() != null)
            questionablePerson.setPassword(encoder.encode((r.getPassword())));
        if(r.getFirstName() != null)
            questionablePerson.setFirstName(r.getFirstName());
        if(r.getLastName() != null)
            questionablePerson.setLastName(r.getLastName());
        if(r.getAge() != 0) //??????
            questionablePerson.setAge(r.getAge());
        if(r.getEducation() != null)
            questionablePerson.setEducation(r.getEducation());
        if(r.getSkills() != null)
            questionablePerson.setSkills(r.getSkills());

        personRepo.save(questionablePerson);
    }

    public void editCompany(EditCompanyRequest r) throws Exception {
        UserCompany questionableCompany = companyRepo.findUserCompanyByUsername((r.getOldEmail()));
        if(r.getEmail() != null) {
            if (personRepo.findUserPersonByUsername(r.getEmail()) != null
                    || companyRepo.findUserCompanyByUsername(r.getEmail()) != null)
                throw new Exception("An account with this email already exists");
            else questionableCompany.setUsername(r.getEmail());
        }
        if(r.getPassword() != null)
            questionableCompany.setPassword(encoder.encode((r.getPassword())));
        if(r.getName() != null)
            questionableCompany.setName(r.getName());
        if(r.getDescription() != null)
            questionableCompany.setDescription(r.getDescription());
        if(r.getEmployeeCount() != 0)
            questionableCompany.setEmployeeCount(r.getEmployeeCount());
        if(r.getFoundingYear() != 0)
            questionableCompany.setFoundingYear(r.getFoundingYear());
        if(r.getAddress() != null)
            questionableCompany.setAddress(r.getAddress());
        companyRepo.save(questionableCompany);
    }
}
