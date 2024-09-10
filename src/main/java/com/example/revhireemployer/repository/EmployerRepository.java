package com.example.revhireemployer.repository;

import com.example.revhireemployer.model.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, Long> {
    // Custom queries can be added here if needed
}
