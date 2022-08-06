package com.example.CollegeManagement.Service;

import com.example.CollegeManagement.Entity.Course;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;

@Service
public interface CourseService {
    ResponseEntity<HttpResponse> register(Course course);
    ResponseEntity<HttpResponse> update(Long id, Course course);
    ResponseEntity<HttpResponse> delete(Long id);
//    ResponseEntity<?> getStream(String stream);


}
