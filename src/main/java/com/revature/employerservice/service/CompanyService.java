package com.revature.employerservice.service;

import com.revature.employerservice.exceptions.CompanyNotFoundException;
import com.revature.employerservice.model.Company;
import com.revature.employerservice.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public Company addCompany(Company company) {
        return companyRepository.save(company);
    }

    public Company updateCompany(Long id, Company companyDetails) {
        Company existingCompany = companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException("Company not found with id: " + id));

        if (companyDetails.getName() != null) {
            existingCompany.setName(companyDetails.getName());
        }
        if (companyDetails.getEmailAddress() != null) {
            existingCompany.setEmailAddress(companyDetails.getEmailAddress());
        }
        if (companyDetails.getContactNumber() != null) {
            existingCompany.setContactNumber(companyDetails.getContactNumber());
        }
        if (companyDetails.getCompanyAddress() != null) {
            existingCompany.setCompanyAddress(companyDetails.getCompanyAddress());
        }

        return companyRepository.save(existingCompany);
    }

    public void deleteCompany(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException("Company not found with id: " + id));
        companyRepository.delete(company);
    }

    public Company fetchCompanyById(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException("Company not found with id: " + id));
    }

    public List<Company> fetchAllCompanies() {
        return companyRepository.findAll();
    }
}
