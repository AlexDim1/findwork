package com.findwork.findwork.Repositories;

import com.findwork.findwork.Entities.Users.UserPerson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserPersonRepository extends JpaRepository<UserPerson, UUID> {
    UserPerson findUserPersonByUsername(String username);
}
