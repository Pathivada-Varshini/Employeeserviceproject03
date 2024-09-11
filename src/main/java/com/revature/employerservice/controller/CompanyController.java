package com.revature.employerservice.controller;

import com.revature.employerservice.model.Company;
import com.revature.employerservice.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping("/add")
    public ResponseEntity<Company> addCompany(@RequestBody Company company) {
        Company savedCompany = companyService.addCompany(company);
        return new ResponseEntity<>(savedCompany, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Company> updateCompany(@PathVariable Long id, @RequestBody Company companyDetails) {
        Company updatedCompany = companyService.updateCompany(id, companyDetails);
        return ResponseEntity.ok(updatedCompany);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> fetchCompanyById(@PathVariable Long id) {
        return ResponseEntity.ok(companyService.fetchCompanyById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Company>> fetchAllCompanies() {
        return ResponseEntity.ok(companyService.fetchAllCompanies());
    }
}
