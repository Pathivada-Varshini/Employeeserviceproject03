package com.example.revhireemployer.controller;

import com.example.revhireemployer.exceptions.EmployerNotFoundException;
import com.example.revhireemployer.model.Employer;
import com.example.revhireemployer.service.EmployerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employers")
public class EmployerController {

    @Autowired
    private EmployerService employerService;

    @GetMapping
    public List<Employer> getAllEmployers() {
        return employerService.getAllEmployers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employer> getEmployerById(@PathVariable Long id) {
        Optional<Employer> employer = employerService.getEmployerById(id);
        return employer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Employer> createEmployer(@RequestBody Employer employer, @RequestHeader("user-info") String createdBy) {
        Employer createdEmployer = employerService.createEmployer(employer, createdBy);
        return ResponseEntity.ok(createdEmployer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employer> updateEmployer(@PathVariable Long id, @RequestBody Employer employerDetails,
                                                   @RequestHeader("user-info") String modifiedBy) {
        try {
            Employer updatedEmployer = employerService.updateEmployer(id, employerDetails, modifiedBy);
            return ResponseEntity.ok(updatedEmployer);
        } catch (EmployerNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployer(@PathVariable Long id) {
        try {
            employerService.deleteEmployer(id);
            return ResponseEntity.noContent().build();
        } catch (EmployerNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
