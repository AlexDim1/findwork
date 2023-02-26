package com.findwork.findwork.Repositories;

import com.findwork.findwork.Entities.Users.UserPerson;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;


//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
class UserPersonRepositoryTest {
    @Autowired
    private UserPersonRepository repo;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Disabled("Just for learning purposes")
    @Test
    void registerPerson() throws Exception
    {
        for(int i = 1; i<= 7; i++)
        {
            UserPerson person = new UserPerson();
            person.setUsername("TestUser"+ i +"@gmail.com");
            person.setPassword(encoder.encode("12345678"));
            person.setFirstName("TestUser");
            person.setLastName(Integer.toString(i));
            person.setBirthDate(LocalDate.parse("1999-12-27"));

            if(repo.findUserPersonByUsername(person.getUsername()) == null)
            {
                repo.save(person);
            }
            assertNotNull(repo.findUserPersonByUsername(person.getUsername()));
        }
    }
}