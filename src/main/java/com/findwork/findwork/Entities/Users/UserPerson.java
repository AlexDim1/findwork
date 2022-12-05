package com.findwork.findwork.Entities.Users;

import com.findwork.findwork.Entities.JobApplication;
import com.findwork.findwork.Entities.UserSavedOffer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserPerson implements UserDetails {
    @Id
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private String education;

    private String skills;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<JobApplication> jobApplications;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<UserSavedOffer> savedOffers;

    public UserPerson(String username, String password, String firstName, String lastName, LocalDate birthDate) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("User");
        return Collections.singletonList(authority);
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public int getAge() {
        return Period.between(birthDate, java.time.LocalDate.now()).getYears();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
