package com.example.CollegeManagement.Repository;

import com.example.CollegeManagement.Entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
