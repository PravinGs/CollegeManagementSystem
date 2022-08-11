package com.example.CollegeManagement.Repository;

import com.example.CollegeManagement.Entity.Accountant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountantRepository extends JpaRepository<Accountant, Long> {
    boolean existsByEmail(String email);
    Accountant findByEmail(String email);
}
