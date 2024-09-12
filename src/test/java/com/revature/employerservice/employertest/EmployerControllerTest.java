package com.revature.employerservice.employertest;

import com.revature.employerservice.controller.EmployerController;
import com.revature.employerservice.model.Employer;
import com.revature.employerservice.service.EmployerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class EmployerControllerTest {

    @Mock
    private EmployerService employerService;

    @InjectMocks
    private EmployerController employerController;

    private Employer employer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        employer = new Employer(
                1L,
                "testuser",
                "test@example.com",
                "John",
                "Doe",
                "1234567890",
                "123 Main St",
                "hashedPassword123",  // Assuming this is a hashed password
                null,
                null
        );
    }

    @Test
    public void registerEmployer_Success() {
        when(employerService.addEmployer(any(Employer.class))).thenReturn(employer);
        ResponseEntity<Employer> response = employerController.registerEmployer(employer);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employer.getEmail(), response.getBody().getEmail());
    }

    @Test
    public void getEmployerById_Success() {
        when(employerService.fetchEmployerById(1L)).thenReturn(employer);
        ResponseEntity<Employer> response = employerController.getEmployerById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employer.getEmail(), response.getBody().getEmail());
    }

    @Test
    public void getAllEmployers_Success() {
        List<Employer> employerList = Arrays.asList(employer);
        when(employerService.fetchAllEmployers()).thenReturn(employerList);
        ResponseEntity<List<Employer>> response = employerController.getAllEmployers();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void updateEmployer_Success() {
        when(employerService.updateEmployer(1L, employer)).thenReturn(employer);
        ResponseEntity<Employer> response = employerController.updateEmployer(1L, employer);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employer.getEmail(), response.getBody().getEmail());
    }

    @Test
    public void deleteEmployer_Success() {
        // Mocking service call and performing the deletion
        doNothing().when(employerService).deleteEmployer(1L);
        ResponseEntity<Void> response = employerController.deleteEmployer(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
