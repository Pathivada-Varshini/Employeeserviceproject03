package com.revature.employerservice.controller;

import com.revature.employerservice.model.Employer;
import com.revature.employerservice.service.EmployerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employers")
public class EmployerController {

    @Autowired
    private EmployerService employerService;

    @PostMapping("/register")
    public ResponseEntity<Employer> registerEmployer(@RequestBody Employer employer) {
        Employer savedEmployer = employerService.addEmployer(employer);
        return ResponseEntity.ok(savedEmployer);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Employer> updateEmployer(@PathVariable Long id, @RequestBody Employer employerDetails) {
        Employer updatedEmployer = employerService.updateEmployer(id, employerDetails);
        return ResponseEntity.ok(updatedEmployer);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEmployer(@PathVariable Long id) {
        employerService.deleteEmployer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employer> getEmployerById(@PathVariable Long id) {
        Employer employer = employerService.fetchEmployerById(id);
        return ResponseEntity.ok(employer);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Employer>> getAllEmployers() {
        List<Employer> employers = employerService.fetchAllEmployers();
        return ResponseEntity.ok(employers);
    }

    @PostMapping("/login")
    public ResponseEntity<Employer> login(@RequestParam String email, @RequestParam String password) {
        Employer employer = employerService.login(email, password);
        return ResponseEntity.ok(employer);
    }
}
