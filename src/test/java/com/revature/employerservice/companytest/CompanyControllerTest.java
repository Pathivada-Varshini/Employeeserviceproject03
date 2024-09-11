package com.revature.employerservice.companytest;

import com.revature.employerservice.controller.CompanyController;
import com.revature.employerservice.model.Company;
import com.revature.employerservice.service.CompanyService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class CompanyControllerTest {

    @Mock
    private CompanyService companyService;

    @InjectMocks
    private CompanyController companyController;

    public CompanyControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddCompany() {
        Company company = new Company();
        when(companyService.addCompany(any(Company.class))).thenReturn(company);

        ResponseEntity<Company> response = companyController.addCompany(company);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testUpdateCompany() {
        Company company = new Company();
        when(companyService.updateCompany(anyLong(), any(Company.class))).thenReturn(company);

        ResponseEntity<Company> response = companyController.updateCompany(1L, company);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testDeleteCompany() {
        doNothing().when(companyService).deleteCompany(anyLong());

        ResponseEntity<Void> response = companyController.deleteCompany(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testFetchCompanyById() {
        Company company = new Company();
        when(companyService.fetchCompanyById(anyLong())).thenReturn(company);

        ResponseEntity<Company> response = companyController.fetchCompanyById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testFetchAllCompanies() {
        Company company1 = new Company();
        Company company2 = new Company();
        when(companyService.fetchAllCompanies()).thenReturn(Arrays.asList(company1, company2));

        ResponseEntity<List<Company>> response = companyController.fetchAllCompanies();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }
}
