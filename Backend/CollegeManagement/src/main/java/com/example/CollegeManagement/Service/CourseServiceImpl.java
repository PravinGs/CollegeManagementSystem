package com.example.CollegeManagement.Service;

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
    public ResponseEntity<HttpResponse> register(Course course) {
        if (course != null) {
            courseRepository.save(course);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @Override
    public ResponseEntity<HttpResponse> update(Long id, Course course) {
        if (courseRepository.existsById(id)) {
            Course c = courseRepository.findById(id).get();
            if (course.getStreamId() != null && "".equals(course.getStreamId())) c.setStreamId(course.getStreamId());

            courseRepository.save(c);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @Override
    public ResponseEntity<HttpResponse> delete(Long id) {
        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
//    @Override
//    public ResponseEntity<?> getStream(String stream) {
//        Course course = courseRepository.findByStream(stream.toUpperCase());
//        if (course == null) {
//            return ResponseEntity.badRequest().body("No stream available with this name");
//        }
//        return ResponseEntity.ok().body(course);
//    }


}
