package com.example.CollegeManagement.Service;

import com.example.CollegeManagement.Entity.Accountant;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;
import java.util.List;

@Service
public interface AccountantService extends UserDetailsService {
    ResponseEntity<?> register(Accountant accountant);
    List<String> getUnPaidStudents();
    ResponseEntity<HttpResponse> login(Accountant accountant);
    ResponseEntity<HttpResponse> delete(Long id);
    Accountant findByEmail(String email);
}
