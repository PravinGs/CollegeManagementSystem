package com.example.CollegeManagement.Service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


public interface LogService {
    ResponseEntity<?> getAttendance(Long id);

}
