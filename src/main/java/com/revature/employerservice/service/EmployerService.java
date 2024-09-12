package com.revature.employerservice.service;

import com.revature.employerservice.exceptions.EmployerNotFoundException;
import com.revature.employerservice.model.Company;
import com.revature.employerservice.model.Employer;
import com.revature.employerservice.repository.CompanyRepository;
import com.revature.employerservice.repository.EmployerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployerService {

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private CompanyRepository companyRepository;

    public Employer addEmployer(Employer employer) {
        // Get company details from the employer object
        Company company = employer.getCompany();
        if (company != null) {
            // Check if the company already exists by unique field (like email or name)
            Company existingCompany = companyRepository.findByEmailAddress(company.getEmailAddress());
            if (existingCompany != null) {
                // If the company already exists, use the existing one
                employer.setCompany(existingCompany);
            } else {
                // Save the new company if it doesn't exist
                Company savedCompany = companyRepository.save(company);
                employer.setCompany(savedCompany);
            }
        }

        return employerRepository.save(employer);
    }


    public Employer updateEmployer(Long id, Employer employerDetails) {
        Employer existingEmployer = employerRepository.findById(id)
                .orElseThrow(() -> new EmployerNotFoundException("Employer not found with id: " + id));

        if (employerDetails.getFirstName() != null) {
            existingEmployer.setFirstName(employerDetails.getFirstName());
        }
        if (employerDetails.getLastName() != null) {
            existingEmployer.setLastName(employerDetails.getLastName());
        }
        if (employerDetails.getEmail() != null) {
            existingEmployer.setEmail(employerDetails.getEmail());
        }
        if (employerDetails.getContactNumber() != null) {
            existingEmployer.setContactNumber(employerDetails.getContactNumber());
        }
        if (employerDetails.getEmployeeAddress() != null) {
            existingEmployer.setEmployeeAddress(employerDetails.getEmployeeAddress());
        }

        return employerRepository.save(existingEmployer);
    }

    public void deleteEmployer(Long id) {
        Employer employer = employerRepository.findById(id)
                .orElseThrow(() -> new EmployerNotFoundException("Employer not found with id: " + id));
        employerRepository.delete(employer);
    }

    public Employer fetchEmployerById(Long id) {
        return employerRepository.findById(id)
                .orElseThrow(() -> new EmployerNotFoundException("Employer not found with id: " + id));
    }

    public List<Employer> fetchAllEmployers() {
        return employerRepository.findAll();
    }

    public Employer login(String email, String password) {
        Employer employer = employerRepository.findByEmail(email)
                .orElseThrow(() -> new EmployerNotFoundException("Employer not found with email: " + email));

        if (!employer.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid password");
        }

        return employer;
    }
}
