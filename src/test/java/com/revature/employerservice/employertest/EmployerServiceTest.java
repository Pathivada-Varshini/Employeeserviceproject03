package com.revature.employerservice.employertest;

import com.revature.employerservice.exceptions.EmployerNotFoundException;
import com.revature.employerservice.model.Employer;
import com.revature.employerservice.repository.EmployerRepository;
import com.revature.employerservice.service.EmployerService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class EmployerServiceTest {

    @Mock
    private EmployerRepository employerRepository;

    @InjectMocks
    private EmployerService employerService;

    public EmployerServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddEmployer() {
        Employer employer = new Employer();

        when(employerRepository.save(any(Employer.class))).thenReturn(employer);

        Employer result = employerService.addEmployer(employer);

        assertNotNull(result);
        verify(employerRepository, times(1)).save(employer);
    }

    @Test
    public void testUpdateEmployer() {
        Employer existingEmployer = new Employer();
        existingEmployer.setId(1L);

        Employer employerDetails = new Employer();
        employerDetails.setFirstName("New First Name");

        when(employerRepository.findById(anyLong())).thenReturn(Optional.of(existingEmployer));
        when(employerRepository.save(any(Employer.class))).thenReturn(existingEmployer);

        Employer result = employerService.updateEmployer(1L, employerDetails);

        assertNotNull(result);
        assertEquals("New First Name", result.getFirstName());
        verify(employerRepository, times(1)).save(existingEmployer);
    }

    @Test
    public void testDeleteEmployer() {
        Employer employer = new Employer();
        employer.setId(1L);

        when(employerRepository.findById(anyLong())).thenReturn(Optional.of(employer));

        employerService.deleteEmployer(1L);

        verify(employerRepository, times(1)).delete(employer);
    }

    @Test
    public void testFetchEmployerById() {
        Employer employer = new Employer();
        employer.setId(1L);

        when(employerRepository.findById(anyLong())).thenReturn(Optional.of(employer));

        Employer result = employerService.fetchEmployerById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    public void testFetchEmployerByIdNotFound() {
        when(employerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EmployerNotFoundException.class, () -> employerService.fetchEmployerById(1L));
    }
}
