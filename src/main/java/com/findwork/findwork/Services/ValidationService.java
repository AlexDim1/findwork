package com.findwork.findwork.Services;

import com.findwork.findwork.Requests.*;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {
    public boolean validateLoginRequest(LoginRequest r) {
        if(r.getEmail() == null || r.getPassword() == null)
            return false;

        return !r.getEmail().isEmpty() && !r.getPassword().isEmpty();
    }

    public boolean validateRegisterPersonRequest(RegisterPersonRequest r) {
        if(r.getEmail() == null
        || r.getPassword() == null
        || r.getFirstName() == null
        || r.getLastName() == null)
            return false;

        if(r.getEmail().isEmpty()
        || r.getPassword().isEmpty()
        || r.getFirstName().isEmpty()
        || r.getLastName().isEmpty())
            return false;

        if(!validatePassword(r.getPassword()))
            return false;

        return (validateEmail(r.getEmail()) && validatePassword(r.getPassword()));
    }

    public boolean validateRegisterCompanyRequest(RegisterCompanyRequest r) {
        if(r.getEmail() == null
                || r.getPassword() == null
                || r.getName() == null)
            return false;

        if(r.getEmail().isEmpty()
                || r.getPassword().isEmpty()
                || r.getName().isEmpty())
            return false;

        if(!validatePassword(r.getPassword()))
            return false;
        return validateEmail(r.getEmail());
    }

    private boolean validatePassword(String password) {
        return password.length() >= 8;
    }

    private boolean validateEmail(String email) {
        return email.matches("^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
    }

    public boolean validateEditPersonRequest(EditPersonRequest r) throws Exception {
        String badDataFields = "";

        if(r.getEmail() != null)
            if(validateEmail(r.getEmail()))
                badDataFields += "invalid email format, ";

        if(r.getPassword() != null)
            if(!validatePassword(r.getPassword()))
                badDataFields += "invalid password - should be 8+ symbols, ";
        if(r.getAge() != 0)
            if(r.getAge() < 16) //????????????
                badDataFields += "invalid age - should be >15, ";

        if(badDataFields != "")
            throw new Exception(badDataFields.substring(0, badDataFields.length()-2) + "."); // слага точка вместо последната запетая

        return true;
    }
    public boolean validateEditCompanyRequest(EditCompanyRequest r) throws Exception {
        String badDataFields = "";

        if(r.getEmail() != null)
            if(!validateEmail(r.getEmail()))
                badDataFields += "invalid email format, ";

        if(r.getPassword() != null)
            if(!validatePassword(r.getPassword()))
                badDataFields += "invalid password - should be 8+ symbols, ";
        if(r.getDescription() != null)
            if(r.getDescription().length() < 10) //????????????
                badDataFields += "invalid description - should be 10+ symbols, ";
        if(r.getEmployeeCount() != 0)
            if(r.getEmployeeCount() < 0)
                badDataFields += "invalid employee count - should be >0, ";
        if(r.getFoundingYear() != 0)
            if(r.getFoundingYear() < 1900)
                badDataFields += "invalid founding year - should be 1900+, ";

        if(badDataFields != "")
            throw new Exception(badDataFields.substring(0, badDataFields.length()-2) + "."); // слага точка вместо последната запетая

        return true;
    }

    public Boolean validateCreateJobOfferRequest(CreateJobOfferRequest r) {
        if(r.getTitle() == null
                || r.getRequirements() == null
                || r.getLocation() == null
                || r.getSalary() == null
                || r.getJobLevel() == null
                || r.getJobCategory() == null
                || r.getCompany() == null)
            return false;

        if(r.getTitle().isEmpty()
                || r.getRequirements().isEmpty()
                || r.getLocation().isEmpty()
                || r.getSalary().isEmpty())
            return false;

        return true;
    }
}
