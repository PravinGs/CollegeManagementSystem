package com.example.CollegeManagement.Repository;

import com.example.CollegeManagement.Entity.FeesStructure;
import com.example.CollegeManagement.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeesStructureRepository extends JpaRepository<FeesStructure, Long> {
    boolean existsByStudent(Student student);
    FeesStructure findByStudent(Student student);
}
