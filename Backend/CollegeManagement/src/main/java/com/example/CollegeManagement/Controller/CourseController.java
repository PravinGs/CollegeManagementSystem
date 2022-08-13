package com.example.CollegeManagement.Controller;

import com.example.CollegeManagement.Dto.Message;
import com.example.CollegeManagement.Entity.Course;
import com.example.CollegeManagement.Service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Course course) {
        if (course == null && !courseService.existsByStreamId(course.getStreamId()))
            return ResponseEntity.badRequest().body(new Message("Course Already Exists with this name"));
        return courseService.register(course);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody Course course, @PathVariable("id") Long id) {
        if (course == null && !courseService.existsById(id))
            return ResponseEntity.badRequest().body(new Message("No Id available with this id"));
        return courseService.update(id, course);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        if (!courseService.existsById(id))
            return ResponseEntity.badRequest().body(new Message("No Id available with this id"));
        return courseService.delete(id);
    }

}
