package com.example.CollegeManagement.Service;

import com.example.CollegeManagement.Dto.LoginDto;
import com.example.CollegeManagement.Dto.StudentDto;
import com.example.CollegeManagement.Entity.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;

@Service
public interface StudentService {
    Student register(StudentDto dto);
//    ResponseEntity<HttpResponse> login(LoginDto dto);
    ResponseEntity<HttpResponse> update(Long id, StudentDto dto);
    Student getById(Long id);
    ResponseEntity<HttpResponse> delete(Long id);
}
