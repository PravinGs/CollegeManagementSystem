package com.example.CollegeManagement.Service;

import com.example.CollegeManagement.Dto.AttendanceDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;
import java.util.List;

@Service
public interface AttendanceService {
    ResponseEntity<HttpResponse> present(AttendanceDto dto);
    List<String> presentByDate(AttendanceDto dto);
}
