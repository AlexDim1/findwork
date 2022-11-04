package com.findwork.findwork.Repositories;

import com.findwork.findwork.Entities.Users.UserCompany;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserCompanyRepository extends JpaRepository<UserCompany, UUID> {
    Optional<UserCompany> findUserCompanyByUsername(String username);
}
