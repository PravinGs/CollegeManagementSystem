package com.example.CollegeManagement.Service;

import com.example.CollegeManagement.Dto.AttendanceDto;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;
import java.util.List;

@Service
public interface AttendanceService {
    ResponseEntity<?> present(AttendanceDto dto);
    List<String> studentsPresentByDate(AttendanceDto dto);
    ResponseEntity<?> getStudentAttendanceById(Long id);
    @Scheduled(cron = "${corn.expression}")
    void addWorkingDays();
}
