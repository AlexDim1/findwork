package com.findwork.findwork.Services;

import com.findwork.findwork.Entities.Users.UserCompany;
import com.findwork.findwork.Entities.Users.UserPerson;
import com.findwork.findwork.Repositories.UserCompanyRepository;
import com.findwork.findwork.Repositories.UserPersonRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserCompanyRepository companyRepo;
    private final UserPersonRepository personRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserPerson foundPerson = personRepo.findUserPersonByUsername(username);
        UserCompany foundCompany = companyRepo.findUserCompanyByUsername(username);

        if(foundCompany == null && foundPerson == null) {
            throw new UsernameNotFoundException("No user found");
        }

        if(foundPerson != null)
            return foundPerson;

        return foundCompany;
    }

    public UserService(UserCompanyRepository companyRepo, UserPersonRepository personRepo) {
        this.companyRepo = companyRepo;
        this.personRepo = personRepo;
    }
}
