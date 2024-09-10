package com.example.revhireemployer.service;

import com.example.revhireemployer.exceptions.EmployerNotFoundException;
import com.example.revhireemployer.model.Employer;
import com.example.revhireemployer.repository.EmployerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployerService {

    @Autowired
    private EmployerRepository employerRepository;

    public Employer createEmployer(Employer employer, String createdBy) {
        if (employer == null) {
            throw new IllegalArgumentException("Employer cannot be null");
        }
        employer.setCreatedBy(createdBy);
        return employerRepository.save(employer);
    }


    public List<Employer> getAllEmployers() {
        return employerRepository.findAll();
    }

    public Optional<Employer> getEmployerById(Long id) {
        return employerRepository.findById(id);
    }

    public Employer updateEmployer(Long id, Employer employerDetails, String modifiedBy) {
        Employer existingEmployer = getEmployerById(id)
                .orElseThrow(() -> new EmployerNotFoundException("Employer with ID " + id + " not found"));

        if (employerDetails.getName() != null && !employerDetails.getName().isEmpty()) {
            existingEmployer.setName(employerDetails.getName());
        }
        if (employerDetails.getEmail() != null && !employerDetails.getEmail().isEmpty()) {
            existingEmployer.setEmail(employerDetails.getEmail());
        }
        if (employerDetails.getUrl() != null && !employerDetails.getUrl().isEmpty()) {
            existingEmployer.setUrl(employerDetails.getUrl());
        }
        existingEmployer.setModifiedBy(modifiedBy);

        return employerRepository.save(existingEmployer);
    }

    public void deleteEmployer(Long id) {
        if (!employerRepository.existsById(id)) {
            throw new EmployerNotFoundException("Employer with ID " + id + " not found");
        }
        employerRepository.deleteById(id);
    }
}
