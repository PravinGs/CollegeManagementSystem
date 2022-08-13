package com.example.CollegeManagement.Service;

import com.example.CollegeManagement.Dto.Message;
import com.example.CollegeManagement.Entity.Course;
import com.example.CollegeManagement.Repository.CourseRepository;
import com.example.CollegeManagement.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.net.http.HttpResponse;

@Service
@Transactional
public class CourseServiceImpl implements CourseService{
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Override
    public ResponseEntity<?> register(Course course) {
        courseRepository.save(course);
        return ResponseEntity.ok().body(new Message("Course Added Successfully..."));
    }
    @Override
    public ResponseEntity<?> update(Long id, Course course) {
        Course c = courseRepository.findById(id).get();
        if (course.getStreamId() != null && "".equals(course.getStreamId())) {
            c.setStreamId(course.getStreamId());
            courseRepository.save(c);
            return ResponseEntity.ok().body(new Message("Course Updated Successfully."));
        }
        return ResponseEntity.badRequest().body(new Message("Check your details names is empty"));
    }
    @Override
    public ResponseEntity<?> delete(Long id) {
        courseRepository.deleteById(id);
        return ResponseEntity.ok().body("Course Removed Successfully..");
    }

    @Override
    public boolean existsByStreamId(String streamId) {
        return courseRepository.existsByStreamId(streamId);
    }
    @Override
    public boolean existsById(Long id) {
        return courseRepository.existsById(id);
    }


}
