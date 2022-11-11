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

    public boolean validateEditPersonRequest(EditPersonRequest r) {
        if(r.getEmail() != null)
            if(validateEmail(r.getEmail()))
                return false;

        if(r.getPassword() != null)
            if(!validatePassword(r.getPassword()))
                return false;
        if(r.getAge() != 0)
            if(r.getAge() < 16) //????????????
                return false;

        return true;
    }
    public boolean validateEditCompanyRequest(EditCompanyRequest r) throws Exception {
        String badDataFields = "";

        if(r.getEmail() != null)
            if(!validateEmail(r.getEmail()))
                badDataFields += "invalid email, ";

        if(r.getPassword() != null)
            if(!validatePassword(r.getPassword()))
                badDataFields += "invalid password, ";
        if(r.getDescription() != null)
            if(r.getDescription().length() < 10) //????????????
                badDataFields += "invalid description, ";
        if(r.getEmployeeCount() != 0)
            if(r.getEmployeeCount() < 0)
                badDataFields += "invalid employee count, ";
        if(r.getFoundingYear() != 0)
            if(r.getFoundingYear() < 1900)
                badDataFields += "invalid founding year, ";

        if(badDataFields != "")
            throw new Exception(badDataFields.substring(0, badDataFields.length()-2) + "."); // слага точка вместо последната запетая

        return true;
    }

}
