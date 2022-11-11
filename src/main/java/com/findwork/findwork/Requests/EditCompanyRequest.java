package com.findwork.findwork.Requests;

public class EditCompanyRequest {
    private String oldEmail;

    private String email;

    private String password;

    private String name;

    private String description;

    private int employeeCount;

    private int foundingYear;

    private String address;

    public EditCompanyRequest(String oldEmail, String email, String password, String name, String description, int employeeCount, int foundingYear, String address)
    {
        this.oldEmail = oldEmail;
        this.email = email;
        this.password = password;
        this.name = name;
        this.description = description;
        this.employeeCount = employeeCount;
        this.foundingYear = foundingYear;
        this.address = address;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(int employeeCount) {
        this.employeeCount = employeeCount;
    }

    public int getFoundingYear() {
        return foundingYear;
    }

    public void setFoundingYear(int foundingYear) {
        this.foundingYear = foundingYear;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
