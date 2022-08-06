package com.example.CollegeManagement.Service;

import com.example.CollegeManagement.Entity.Course;
import com.example.CollegeManagement.Repository.CourseRepository;
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
            if (course.getSubject1() != null && "".equals(course.getSubject1())) c.setSubject1(course.getSubject1());
            if (course.getSubject2() != null && "".equals(course.getSubject2())) c.setSubject1(course.getSubject2());
            if (course.getSubject3() != null && "".equals(course.getSubject3())) c.setSubject1(course.getSubject3());
            if (course.getSubject4() != null && "".equals(course.getSubject4())) c.setSubject1(course.getSubject4());
            if (course.getSubject5() != null && "".equals(course.getSubject5())) c.setSubject1(course.getSubject5());
            if (course.getSubject6() != null && "".equals(course.getSubject6())) c.setSubject1(course.getSubject6());
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
}
