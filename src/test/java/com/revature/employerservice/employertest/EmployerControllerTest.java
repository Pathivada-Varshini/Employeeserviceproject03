package com.revature.employerservice.employertest;

import com.revature.employerservice.controller.EmployerController;
import com.revature.employerservice.model.Employer;
import com.revature.employerservice.service.EmployerService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class EmployerControllerTest {

    @Mock
    private EmployerService employerService;

    @InjectMocks
    private EmployerController employerController;

    public EmployerControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddEmployer() {
        Employer employer = new Employer();
        when(employerService.addEmployer(any(Employer.class))).thenReturn(employer);

        ResponseEntity<Employer> response = employerController.addEmployer(employer);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }


    @Test
    public void testUpdateEmployer() {
        Employer employer = new Employer();
        when(employerService.updateEmployer(anyLong(), any(Employer.class))).thenReturn(employer);

        ResponseEntity<Employer> response = employerController.updateEmployer(1L, employer);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testDeleteEmployer() {
        doNothing().when(employerService).deleteEmployer(anyLong());

        ResponseEntity<Void> response = employerController.deleteEmployer(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testFetchEmployerById() {
        Employer employer = new Employer();
        when(employerService.fetchEmployerById(anyLong())).thenReturn(employer);

        ResponseEntity<Employer> response = employerController.fetchEmployerById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testFetchAllEmployers() {
        Employer employer1 = new Employer();
        Employer employer2 = new Employer();
        when(employerService.fetchAllEmployers()).thenReturn(Arrays.asList(employer1, employer2));

        ResponseEntity<List<Employer>> response = employerController.fetchAllEmployers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }
}
