package com.example.CollegeManagement.Repository;

import com.example.CollegeManagement.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {


    Student findByRegisterNo(String regNo);
    Boolean existsByRegisterNo(String regNo);
    @Modifying
    @Query(
            nativeQuery = true,
            value = "select email from college.student as s left join college.fees_structure as f on f.student_id = s.id where f.total_fees > 0;"
    )
    List<String> getStudentEmailsByFees();

    Student findByEmail(String email);
}
