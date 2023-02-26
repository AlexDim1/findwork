package com.findwork.findwork.Repositories;

import com.findwork.findwork.Entities.Users.UserCompany;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserCompanyRepository extends JpaRepository<UserCompany, UUID> {
    UserCompany findUserCompanyByUsername(String username);
    UserCompany findUserCompanyById(UUID id);
    UserCompany findUserCompanyByName(String name);
    List<UserCompany> findUserCompaniesByUsernameStartsWith(String username);
}
