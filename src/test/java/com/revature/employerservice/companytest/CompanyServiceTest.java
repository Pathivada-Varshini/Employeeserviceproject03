package com.revature.employerservice.companytest;

import com.revature.employerservice.exceptions.CompanyNotFoundException;
import com.revature.employerservice.model.Company;
import com.revature.employerservice.repository.CompanyRepository;
import com.revature.employerservice.service.CompanyService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class CompanyServiceTest {

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyService companyService;

    public CompanyServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddCompany() {
        Company company = new Company();
        when(companyRepository.save(any(Company.class))).thenReturn(company);

        Company result = companyService.addCompany(company);

        assertNotNull(result);
        verify(companyRepository, times(1)).save(company);
    }

    @Test
    public void testUpdateCompany() {
        Company existingCompany = new Company();
        existingCompany.setId(1L);

        Company companyDetails = new Company();

        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(existingCompany));
        when(companyRepository.save(any(Company.class))).thenReturn(existingCompany);

        Company result = companyService.updateCompany(1L, companyDetails);

        assertNotNull(result);
        verify(companyRepository, times(1)).save(existingCompany);
    }

    @Test
    public void testDeleteCompany() {
        Company company = new Company();
        company.setId(1L);

        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));

        companyService.deleteCompany(1L);

        verify(companyRepository, times(1)).delete(company);
    }

    @Test
    public void testFetchCompanyById() {
        Company company = new Company();
        company.setId(1L);

        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));

        Company result = companyService.fetchCompanyById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    public void testFetchCompanyByIdNotFound() {
        when(companyRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CompanyNotFoundException.class, () -> companyService.fetchCompanyById(1L));
    }
}
