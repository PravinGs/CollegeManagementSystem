package com.example.CollegeManagement.Repository;

import com.example.CollegeManagement.Entity.Mark;
import com.example.CollegeManagement.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarkRepository extends JpaRepository<Mark, Long> {

    boolean existsByStudent(Student student);

    void deleteByStudent(Student student);
}
