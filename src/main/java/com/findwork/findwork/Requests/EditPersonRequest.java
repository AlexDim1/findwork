package com.findwork.findwork.Requests;

import org.springframework.web.bind.annotation.RequestParam;

public class EditPersonRequest {
    private String oldEmail;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private int age;

    private String education;

    private String skills;

    public EditPersonRequest(String oldEmail, String email, String password, String firstName, String lastName, int age, String education, String skills) {
        this.oldEmail = oldEmail;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.education = education;
        this.skills = skills;
    }

    public String getOldEmail() {
        return oldEmail;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }
}
