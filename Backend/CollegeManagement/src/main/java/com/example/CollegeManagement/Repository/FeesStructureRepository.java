package com.example.CollegeManagement.Repository;

import com.example.CollegeManagement.Entity.FeesStructure;
import com.example.CollegeManagement.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FeesStructureRepository extends JpaRepository<FeesStructure, Long> {

    FeesStructure findByStudent(Student student);

    void deleteByStudent(Student student);

    boolean existsByStudent(Student student);
}
