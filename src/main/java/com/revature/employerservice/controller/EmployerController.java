package com.revature.employerservice.controller;

import com.revature.employerservice.model.Employer;
import com.revature.employerservice.service.EmployerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employers")
public class EmployerController {

    @Autowired
    private EmployerService employerService;

    @PostMapping("/add")
    public ResponseEntity<Employer> addEmployer(@RequestBody Employer employer) {
        Employer savedEmployer = employerService.addEmployer(employer);
        return new ResponseEntity<>(savedEmployer, HttpStatus.CREATED);
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
    public ResponseEntity<Employer> fetchEmployerById(@PathVariable Long id) {
        return ResponseEntity.ok(employerService.fetchEmployerById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Employer>> fetchAllEmployers() {
        return ResponseEntity.ok(employerService.fetchAllEmployers());
    }

    @PostMapping("/login")
    public ResponseEntity<Employer> login(@RequestParam String email, @RequestParam String password) {
        Employer employer = employerService.login(email, password);
        return ResponseEntity.ok(employer);
    }
}
