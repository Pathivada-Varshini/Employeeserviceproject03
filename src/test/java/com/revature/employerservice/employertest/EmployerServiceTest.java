package com.revature.employerservice.employertest;
import com.revature.employerservice.exceptions.EmployerNotFoundException;
import com.revature.employerservice.model.Employer;
import com.revature.employerservice.repository.EmployerRepository;
import com.revature.employerservice.service.EmployerService;
import com.revature.employerservice.util.PasswordUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EmployerServiceTest {

    @Mock
    private EmployerRepository employerRepository;

    @InjectMocks
    private EmployerService employerService;

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
                PasswordUtils.hashPassword("password123"),
                null,
                null
        );
    }

    @Test
    public void addEmployer_Success() {
        when(employerRepository.save(any(Employer.class))).thenReturn(employer);
        Employer savedEmployer = employerService.addEmployer(employer);
        assertEquals(employer.getEmail(), savedEmployer.getEmail());
        assertTrue(PasswordUtils.verifyPassword("password123", savedEmployer.getPassword()));
    }

    @Test
    public void fetchEmployerById_Success() {
        when(employerRepository.findById(1L)).thenReturn(Optional.of(employer));
        Employer fetchedEmployer = employerService.fetchEmployerById(1L);
        assertEquals(employer.getEmail(), fetchedEmployer.getEmail());
    }

    @Test
    public void fetchEmployerById_NotFound() {
        when(employerRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EmployerNotFoundException.class, () -> employerService.fetchEmployerById(1L));
    }

    @Test
    public void updateEmployer_Success() {
        when(employerRepository.findById(1L)).thenReturn(Optional.of(employer));
        when(employerRepository.save(any(Employer.class))).thenReturn(employer);
        Employer updatedEmployer = employerService.updateEmployer(1L, employer);
        assertEquals(employer.getEmail(), updatedEmployer.getEmail());
    }

    @Test
    public void deleteEmployer_Success() {
        when(employerRepository.findById(1L)).thenReturn(Optional.of(employer));
        doNothing().when(employerRepository).delete(employer);
        employerService.deleteEmployer(1L);
        verify(employerRepository, times(1)).delete(employer);
    }

    @Test
    public void login_Success() {
        when(employerRepository.findByEmail("test@example.com")).thenReturn(Optional.of(employer));
        Employer loggedInEmployer = employerService.login("test@example.com", "password123");
        assertEquals(employer.getEmail(), loggedInEmployer.getEmail());
    }

    @Test
    public void login_Failure_InvalidPassword() {
        when(employerRepository.findByEmail("test@example.com")).thenReturn(Optional.of(employer));
        assertThrows(IllegalArgumentException.class, () -> employerService.login("test@example.com", "wrongpassword"));
    }

    @Test
    public void login_Failure_EmployerNotFound() {
        when(employerRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        assertThrows(EmployerNotFoundException.class, () -> employerService.login("test@example.com", "password123"));
    }
}
