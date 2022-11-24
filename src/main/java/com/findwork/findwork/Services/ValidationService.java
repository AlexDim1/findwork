package com.findwork.findwork.Services;

import com.findwork.findwork.Enums.Category;
import com.findwork.findwork.Enums.JobLevel;
import com.findwork.findwork.Repositories.UserCompanyRepository;
import com.findwork.findwork.Requests.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;

@Service
public class ValidationService {
    private final UserCompanyRepository companyRepo;

    public ValidationService(UserCompanyRepository companyRepo) {
        this.companyRepo = companyRepo;
    }

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

    public void validateEditPersonRequest(EditPersonRequest r) throws Exception {
        String badDataFields = "";

        if(r.getEmail() != null)
            if(validateEmail(r.getEmail()))
                badDataFields += "invalid email format, ";

        if(r.getPassword() != null)
            if(!validatePassword(r.getPassword()))
                badDataFields += "invalid password - should be 8+ symbols, ";
        if(r.getBirthDate() != null)
            if(r.getBirthDate().compareTo(LocalDate.parse("1920-01-01")) < 1)
                badDataFields += "invalid age - should be 1920+ year, ";

        if(badDataFields != "")
            throw new Exception(badDataFields.substring(0, badDataFields.length()-2) + "."); // слага точка вместо последната запетая

    }
    public void validateEditCompanyRequest(EditCompanyRequest r) throws Exception {
        String badDataFields = "";

        if(r.getEmail() != null)
            if(!validateEmail(r.getEmail()))
                badDataFields += "invalid email format, ";

        if(r.getPassword() != null)
            if(!validatePassword(r.getPassword()))
                badDataFields += "invalid password - should be 8+ symbols, ";
        if(r.getDescription() != null)
            if(r.getDescription().length() < 10)
                badDataFields += "invalid description - should be 10+ symbols, ";
        if(r.getEmployeeCount() != null)
            if(Integer.parseInt(r.getEmployeeCount()) < 0)
                badDataFields += "invalid employee count - should be >0, ";
        if(r.getFoundingYear() != null)
            if(Integer.parseInt(r.getFoundingYear()) < 1900)
                badDataFields += "invalid founding year - should be 1900+, ";

        if(badDataFields != "")
            throw new Exception(badDataFields.substring(0, badDataFields.length()-2) + "."); // слага точка вместо последната запетая

    }

    public void validateCreateJobOfferRequest(CreateJobOfferRequest r) throws Exception {
        String badDataFields = "";
        if(r.getTitle() != null)
            if(r.getTitle().length() < 4)
                badDataFields += "invalid title - should be 4+ symbols, ";
        if(r.getRequirements() != null)
            if(r.getRequirements().length() < 10)
                badDataFields += "invalid requirements - should be 10+ symbols, ";
        if(r.getLocation() != null)
            if(r.getLocation().length() < 3)
                badDataFields += "invalid location - should be 3+ symbols, ";
        if(r.getSalary() != null)
            if(r.getSalary().length() < 3)
                badDataFields += "invalid salary - should be 3+ symbols, ";
        if(r.getJobLevel() != null)
        {
            boolean contains = false;
            for (JobLevel jl : JobLevel.values())
            {
                if(jl.toString().equals(r.getJobLevel()))
                    contains = true;
            }
            if(!contains)
                badDataFields += "invalid job level, ";
        }
        if(r.getJobCategory() != null)
        {
            boolean contains = false;
            for (Category category : Category.values())
            {
                if(category.toString().equals(r.getJobCategory()))
                    contains = true;
            }
            if(!contains)
                badDataFields += "invalid job category, ";
        }
        if(companyRepo.findUserCompanyById(r.getCompanyId()) == null)
            badDataFields += "invalid company, ";
        if(badDataFields != "")
            throw new Exception(badDataFields.substring(0, badDataFields.length()-2) + "."); // слага точка вместо последната запетая
    }

    public void validateEditJobOfferRequest(EditJobOfferRequest r) throws Exception {
        String badDataFields = "";
        if(r.getTitle() != null)
            if(r.getTitle().length() < 4)
                badDataFields += "invalid title - should be 4+ symbols, ";
        if(r.getDescription() != null)
            if(r.getDescription().length() < 10)
                badDataFields += "invalid description - should be 10+ symbols, ";
        if(r.getRequirements() != null)
            if(r.getRequirements().length() < 10)
                badDataFields += "invalid requirements - should be 10+ symbols, ";
        if(r.getNiceToHave() != null)
            if(r.getNiceToHave().length() < 4)
                badDataFields += "invalid nice to have field - should be 4+ symbols, ";
        if(r.getBenefits() != null)
            if(r.getBenefits().length() < 4)
                badDataFields += "invalid benefits field - should be 4+ symbols, ";
        if(r.getLocation() != null)
            if(r.getLocation().length() < 3)
                badDataFields += "invalid location - should be 3+ symbols, ";
        if(r.getSalary() != null)
            if(r.getSalary().length() < 3)
                badDataFields += "invalid salary - should be 3+ symbols, ";
        if(r.getJobLevel() != null)
        {
            boolean contains = false;
            for (JobLevel jl : JobLevel.values())
            {
                if(jl.toString().equals(r.getJobLevel()))
                contains = true;
            }
            if(!contains)
                badDataFields += "invalid job level, ";
        }
        if(r.getJobCategory() != null)
        {
            boolean contains = false;
            for (Category category : Category.values())
            {
                if(category.toString().equals(r.getJobCategory()))
                 contains = true;
            }
            if(!contains)
                badDataFields += "invalid job category, ";
        }

        if(badDataFields != "")
            throw new Exception(badDataFields.substring(0, badDataFields.length()-2) + "."); // слага точка вместо последната запетая
    }
}
