package com.findwork.findwork.Entities.Users;

import com.findwork.findwork.Entities.JobOffer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserCompany implements UserDetails {
    @Id
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private String username;

    private String password;

    private String name;

    @Column(columnDefinition = "TEXT", length = 5000)
    private String description;

    private String employeeCount;

    private String foundingYear;

    private String address;

    @OneToMany(mappedBy = "company", cascade = {CascadeType.ALL})
    private List<JobOffer> offers;

    public UserCompany(String username, String password, String name, String description, String employeeCount, String foundingYear, String address) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.description = description;
        this.employeeCount = employeeCount;
        this.foundingYear = foundingYear;
        this.address = address;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("Company");
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
