package com.example.CollegeManagement.Controller;

import com.example.CollegeManagement.Dto.*;
import com.example.CollegeManagement.Entity.Exam;
import com.example.CollegeManagement.Event.ExamSendEvent;
import com.example.CollegeManagement.Repository.ExamRepository;
import com.example.CollegeManagement.Repository.SubjectRepository;
import com.example.CollegeManagement.Service.AttendanceService;
import com.example.CollegeManagement.Service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/staff")
public class StaffController {
    @Autowired
    private ExamRepository examRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private ApplicationEventPublisher publisher;
    @Autowired
    private StaffService staffService;
    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/attendance")
    public ResponseEntity<?> attendance(@RequestBody AttendanceDto dto) {
        return attendanceService.present(dto);
    }
    @PostMapping("/attendance/get")
    public List<String> studentsPresentByDate(@RequestBody AttendanceDto dto) {
        return attendanceService.studentsPresentByDate(dto);
    }

    @GetMapping("/attendance/student/{id}")
    public ResponseEntity<?> getStudentAttendanceById(@PathVariable("id") Long id) {
        return attendanceService.getStudentAttendanceById(id);
    }

    @PostMapping("/subject/add")
    public ResponseEntity<?> addSubject(@RequestBody SubjectDto dto) {
        if (dto == null) return ResponseEntity.badRequest().body(new Message(""));
        return staffService.addSubject(dto);
    }
    @DeleteMapping("/subject/delete/{id}")
    public ResponseEntity<?> removeSubject(@PathVariable("id") Long id) {
        if (!staffService.isSubjectExistsById(id)) return ResponseEntity.badRequest().body(new Message("No subject available you can't delete that.."));
        return staffService.removeSubject(id);
    }
    @PostMapping("exam/add")
    public ResponseEntity<?> createExam(@RequestBody ExamDto dto) {
        if (dto == null) return ResponseEntity.badRequest().body(new Message("Invalid Details to create an Exam..."));
        return staffService.createExam(dto);
    }
    @PostMapping("exam/post/{id}")
    public ResponseEntity<?> announceExam(@PathVariable("id") Long id) {
        Optional<Exam> exam = staffService.isExamExistsById(id);
        if (exam.isEmpty())
            return ResponseEntity.ok().body(new Message("No Examination created by this id;"));
        List<String> students = staffService.getAllStudentsByStream(exam.get().getCourse().getStreamId());
        publisher.publishEvent(new ExamSendEvent(exam.get(), students));
        return ResponseEntity.ok().body("Success...");
    }
    @PostMapping("mark/add")
    public ResponseEntity<?> addMark(@RequestBody MarkDto dto) {
        return staffService.addMark(dto);
    }

}
