package com.findwork.findwork.Services;

import com.findwork.findwork.Requests.RegisterCompanyRequest;
import com.findwork.findwork.Requests.RegisterPersonRequest;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {
    public void validateRegisterPersonRequest(RegisterPersonRequest r) throws Exception {
        if (r.getEmail() == null
                || r.getPassword() == null
                || r.getFirstName() == null
                || r.getLastName() == null)
            throw new Exception("Email, names and password are required!");

        if (r.getEmail().isEmpty()
                || r.getPassword().isEmpty()
                || r.getFirstName().isEmpty()
                || r.getLastName().isEmpty())
            throw new Exception("Email, names and password are required!");

        if (!validatePassword(r.getPassword()))
            throw new Exception("Password should be at least 8 characters long!");

        if (!validateEmail(r.getEmail()))
            throw new Exception("Invalid email address!");
    }

    public void validateRegisterCompanyRequest(RegisterCompanyRequest r) throws Exception {
        if (r.getEmail() == null
                || r.getPassword() == null
                || r.getName() == null)
            throw new Exception("Email, name and password are required!");

        if (r.getEmail().isEmpty()
                || r.getPassword().isEmpty()
                || r.getName().isEmpty())
            throw new Exception("Email, name and password are required!");

        if (!validatePassword(r.getPassword()))
            throw new Exception("Password should be at least 8 characters long!");

        if (!validateEmail(r.getEmail()))
            throw new Exception("Invalid email address!");
    }

    private boolean validatePassword(String password) {
        return password.length() >= 8;
    }

    private boolean validateEmail(String email) {
        return email.matches("^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
    }
}
