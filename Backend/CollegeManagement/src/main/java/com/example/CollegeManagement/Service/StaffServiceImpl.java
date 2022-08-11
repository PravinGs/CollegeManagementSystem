package com.example.CollegeManagement.Service;

import com.example.CollegeManagement.Dto.ExamDto;
import com.example.CollegeManagement.Dto.Message;
import com.example.CollegeManagement.Dto.SubjectDto;
import com.example.CollegeManagement.Entity.Course;
import com.example.CollegeManagement.Entity.Exam;
import com.example.CollegeManagement.Entity.Subject;
import com.example.CollegeManagement.Repository.CourseRepository;
import com.example.CollegeManagement.Repository.ExamRepository;
import com.example.CollegeManagement.Repository.StudentRepository;
import com.example.CollegeManagement.Repository.SubjectRepository;
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
}
