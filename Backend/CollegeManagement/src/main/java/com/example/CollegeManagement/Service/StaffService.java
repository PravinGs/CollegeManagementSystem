package com.example.CollegeManagement.Service;

import com.example.CollegeManagement.Dto.ExamDto;
import com.example.CollegeManagement.Dto.MarkDto;
import com.example.CollegeManagement.Dto.SubjectDto;
import com.example.CollegeManagement.Entity.Exam;
import com.example.CollegeManagement.Entity.Subject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface StaffService {
    ResponseEntity<?> addSubject(SubjectDto dto);

    boolean isSubjectExistsById(Long id);

    ResponseEntity<?> removeSubject(Long id);

    ResponseEntity<?> createExam(ExamDto dto);

    Optional<Exam> isExamExistsById(Long id);

    List<String> getAllStudentsByStream(String streamId);

    ResponseEntity<?> addMark(MarkDto dto);
}
