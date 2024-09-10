package com.example.revhireemployer.employertest;

import com.example.revhireemployer.exceptions.EmployerNotFoundException;
import com.example.revhireemployer.model.Employer;
import com.example.revhireemployer.service.EmployerService;
import com.example.revhireemployer.repository.EmployerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EmployerServiceTest {

    @Mock
    private EmployerRepository employerRepository;

    @InjectMocks
    private EmployerService employerService;

    private Employer employer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        employer = new Employer();
        employer.setEmployeeId(1L);
        employer.setName("John Doe");
        employer.setEmail("john.doe@example.com");
        employer.setUrl("http://johndoe.com");
        employer.setCreatedBy("admin");
    }

    @Test
    void testCreateEmployer() {
        when(employerRepository.save(any(Employer.class))).thenReturn(employer);
        Employer savedEmployer = employerService.createEmployer(employer, "admin");
        assertThat(savedEmployer).isNotNull();
        assertThat(savedEmployer.getName()).isEqualTo("John Doe");
        verify(employerRepository, times(1)).save(any(Employer.class));
    }

    @Test
    void testCreateEmployer_NullInput() {
        assertThrows(IllegalArgumentException.class, () -> employerService.createEmployer(null, "admin"));
    }

    @Test
    void testFindEmployerById() {
        when(employerRepository.findById(1L)).thenReturn(Optional.of(employer));
        Optional<Employer> foundEmployer = employerService.getEmployerById(1L);
        assertThat(foundEmployer).isPresent();
        assertThat(foundEmployer.get().getName()).isEqualTo("John Doe");
    }

    @Test
    void testUpdateEmployer() {
        when(employerRepository.findById(1L)).thenReturn(Optional.of(employer));
        employer.setName("Jane Doe");
        when(employerRepository.save(any(Employer.class))).thenReturn(employer);
        Employer updatedEmployer = employerService.updateEmployer(1L, employer, "admin");
        assertThat(updatedEmployer.getName()).isEqualTo("Jane Doe");
        verify(employerRepository, times(1)).save(any(Employer.class));
    }

    @Test
    void testDeleteEmployer() {
        when(employerRepository.existsById(1L)).thenReturn(true);
        doNothing().when(employerRepository).deleteById(1L);
        employerService.deleteEmployer(1L);
        verify(employerRepository, times(1)).deleteById(1L);
    }

    @Test
    void testFindEmployerById_NotFound() {
        when(employerRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(EmployerNotFoundException.class, () -> employerService.getEmployerById(2L)
                .orElseThrow(() -> new EmployerNotFoundException("Employer with ID 2 not found")));
    }
}
