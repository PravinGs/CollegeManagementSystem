package com.example.CollegeManagement.Controller;

import com.example.CollegeManagement.Dto.AttendanceDto;
import com.example.CollegeManagement.Service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;
    @PostMapping("/")
    public ResponseEntity<HttpResponse> attendance(@RequestBody AttendanceDto dto) {
        return attendanceService.present(dto);
    }
    @PostMapping("/date")
    public List<String> presentByDate(@RequestBody AttendanceDto dto) {
        return attendanceService.presentByDate(dto);
    }
}
