package com.example.CollegeManagement.Service;

import com.example.CollegeManagement.Dto.AttendanceDto;
import com.example.CollegeManagement.Entity.Attendance;
import com.example.CollegeManagement.Repository.AttendanceRepository;
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
    @Override
    public ResponseEntity<HttpResponse> present(AttendanceDto dto) {
        if (dto.getStudents().size() > 0 ) {
            List<Long> students = dto.getStudents();
            for (Long id: students) {
                if (Objects.nonNull(attendanceRepository.getByIdAndDate(id, dto.getDate()))) continue;
                attendanceRepository.save(new Attendance(dto.getDate(), studentRepository.findById(id).get()));
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Override
    public List<String> presentByDate(AttendanceDto dto) {
//        Date date = (dto.getDate() == null || !"".equals(dto.getDate())) ? new Date() : dto.getDate();
//        System.out.println(date.toString());
        return attendanceRepository.getNameByDate(dto.getDate());
    }

}
