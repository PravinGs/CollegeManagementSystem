package com.example.CollegeManagement.Service;

import com.example.CollegeManagement.Dto.ExamDto;
import com.example.CollegeManagement.Dto.MarkDto;
import com.example.CollegeManagement.Dto.Message;
import com.example.CollegeManagement.Dto.SubjectDto;
import com.example.CollegeManagement.Entity.*;
import com.example.CollegeManagement.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StaffServiceImpl implements StaffService{
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private ExamRepository examRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private MarkRepository markRepository;
    @Override
    public ResponseEntity<?> addSubject(SubjectDto dto) {
        Course course = courseRepository.findByStreamId(dto.getCourseId());
        if (course == null) return ResponseEntity.badRequest().body(new Message("There is no Course exists in this name " + dto.getCourseId()));
        Subject subject = new Subject(); subject.setSubjectId(dto.getSubjectId()); subject.setSubjectName(dto.getSubjectName()); subject.setCourse(course);
        subjectRepository.save(subject);
        return ResponseEntity.ok().body(new Message("Subject Added Successfully"));
    }

    @Override
    public boolean isSubjectExistsById(Long id) {
        return subjectRepository.existsById(id);
    }
    @Override
    public ResponseEntity<?> removeSubject(Long id) {
        subjectRepository.deleteById(id);
        return ResponseEntity.ok().body(new Message("Subject Removed Successfully"));
    }
    @Override
    public ResponseEntity<?> createExam(ExamDto dto) {
        Course course = courseRepository.findByStreamId(dto.getStreamId());
        if (course == null) return ResponseEntity.badRequest().body(new Message("Invalid Course Id...."));
        Exam exam = new Exam();exam.setExamName(dto.getExamName());exam.setCourse(course);exam.setDate(dto.getDate());
        examRepository.save(exam);
        return ResponseEntity.ok().body(new Message("Examination created..."));
    }
    @Override
    public Optional<Exam> isExamExistsById(Long id) {
        return examRepository.findById(id);
    }
    @Override
    public List<String> getAllStudentsByStream(String course) {
        return studentRepository.findEmailByCourse(course);
    }

    @Override
    public ResponseEntity<?> addMark(MarkDto dto) {
        if (!studentRepository.existsById(dto.getStudentId())) return ResponseEntity.badRequest().body(new Message("Invalid student id"));
        if (!examRepository.existsById(dto.getExamId())) return ResponseEntity.badRequest().body(new Message("No exam has been published with this id"));
        Student student = studentRepository.findById(dto.getStudentId()).get();
        Exam exam = examRepository.findById(dto.getExamId()).get();
        Mark mark = new Mark();
        mark.setExam(exam);
        mark.setStudent(student);
        mark.setMark1(dto.getMark1());
        mark.setMark2(dto.getMark2());
        mark.setMark3(dto.getMark3());
        mark.setMark4(dto.getMark4());
        mark.setMark5(dto.getMark5());
        mark.setMark6(dto.getMark6());
        markRepository.save(mark);
        return ResponseEntity.ok().body(new Message("Mark Updated.."));
    }
}
