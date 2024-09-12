package com.revature.employerservice.service;

import com.revature.employerservice.exceptions.EmployerNotFoundException;
import com.revature.employerservice.model.Employer;
import com.revature.employerservice.repository.EmployerRepository;
import com.revature.employerservice.util.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployerService {

    @Autowired
    private EmployerRepository employerRepository;

    public Employer addEmployer(Employer employer) {
        employer.setPassword(PasswordUtils.hashPassword(employer.getPassword()));
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
        if (employerDetails.getAddress() != null) {
            existingEmployer.setAddress(employerDetails.getAddress());
        }
        if (employerDetails.getUsername() != null) {
            existingEmployer.setUsername(employerDetails.getUsername());
        }
        if (employerDetails.getPassword() != null) {
            existingEmployer.setPassword(PasswordUtils.hashPassword(employerDetails.getPassword()));
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

        if (!PasswordUtils.verifyPassword(password, employer.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }

        return employer;
    }
}
