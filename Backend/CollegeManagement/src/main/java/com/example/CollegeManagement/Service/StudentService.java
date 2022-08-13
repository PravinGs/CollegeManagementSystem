package com.example.CollegeManagement.Service;

import com.example.CollegeManagement.Dto.PaymentDto;
import com.example.CollegeManagement.Dto.StudentDto;
import com.example.CollegeManagement.Entity.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface StudentService extends UserDetailsService {
    ResponseEntity<?> register(StudentDto dto);
//    ResponseEntity<HttpResponse> login(LoginDto dto);
    ResponseEntity<?> update(Long id, StudentDto dto);
    ResponseEntity<?> getById(Long id);
    ResponseEntity<?> delete(Long id);
    boolean existsByEmail(String email);
    boolean existsByRegisterNo(String regNo);
    Student getByEmail(String email);

    ResponseEntity<?> getAttendance(Long id);

    ResponseEntity<?> getStudentsByStream(String stream);

    ResponseEntity<?> getFeesStructure(Long id);

    ResponseEntity<?> payment(PaymentDto dto);
}
