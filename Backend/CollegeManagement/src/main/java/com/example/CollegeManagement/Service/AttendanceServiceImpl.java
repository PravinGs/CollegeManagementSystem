package com.example.CollegeManagement.Service;

import com.example.CollegeManagement.Dto.AttendanceDto;
import com.example.CollegeManagement.Dto.Message;
import com.example.CollegeManagement.Entity.Attendance;
import com.example.CollegeManagement.Entity.Log;
import com.example.CollegeManagement.Repository.AttendanceRepository;
import com.example.CollegeManagement.Repository.LogRepository;
import com.example.CollegeManagement.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class AttendanceServiceImpl implements AttendanceService{
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private LogRepository logRepository;
    @Override
    public ResponseEntity<?> present(AttendanceDto dto) {
        if (dto.getStudents().size() > 0 ) {
            List<Long> students = dto.getStudents();
            for (Long id: students) {
                if (Objects.nonNull(attendanceRepository.getByIdAndDate(id, dto.getDate()))) continue;
                updateLogAttendance(id);
                attendanceRepository.save(new Attendance(dto.getDate(), studentRepository.findById(id).get()));
            }
        }
        return ResponseEntity.ok().body("Done");
    }

    private void updateLogAttendance(Long id) {
        logRepository.updateAttendance(id);
    }

    @Override
    public List<String> studentsPresentByDate(AttendanceDto dto) {
        return attendanceRepository.getNameByDate(dto.getDate());
    }

    @Override
    public ResponseEntity<?> getStudentAttendanceById(Long id) {
        if (studentRepository.existsById(id)) {
            Log log = logRepository.findByStudentId(id);
            return ResponseEntity.ok().body(new Message("Total Working Days : " + log.getWorkingDays() + " Total Present Days : " + log.getPresentDays()));
        }
        return ResponseEntity.badRequest().body(new Message("Invalid Student id!"));
    }
    @Override
    public void addWorkingDays() {
        logRepository.updateWorkingDay();
    }

}
