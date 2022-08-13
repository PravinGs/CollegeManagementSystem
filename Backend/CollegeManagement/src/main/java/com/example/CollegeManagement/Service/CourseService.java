package com.example.CollegeManagement.Service;

import com.example.CollegeManagement.Entity.Course;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;

@Service
public interface CourseService {
    ResponseEntity<?> register(Course course);
    ResponseEntity<?> update(Long id, Course course);
    ResponseEntity<?> delete(Long id);
    boolean existsByStreamId(String streamId);
    boolean existsById(Long id);
}
