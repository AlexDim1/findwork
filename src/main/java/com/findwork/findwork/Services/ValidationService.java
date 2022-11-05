package com.findwork.findwork.Services;

import com.findwork.findwork.Requests.LoginRequest;
import com.findwork.findwork.Requests.RegisterCompanyRequest;
import com.findwork.findwork.Requests.RegisterPersonRequest;
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

        if(validatePassword(r.getPassword()))
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
}
