package com.example.CollegeManagement.Controller;

import com.example.CollegeManagement.Entity.Course;
import com.example.CollegeManagement.Service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping("/register")
    public ResponseEntity<HttpResponse> register(@RequestBody Course course) {
        return courseService.register(course);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<HttpResponse> update(@RequestBody Course course, @PathVariable("id") Long id) {
        return courseService.update(id, course);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpResponse> delete(@PathVariable("id") Long id) {
        return courseService.delete(id);
    }
//    @GetMapping("/get/{stream}")
//    public ResponseEntity<?> getStream(@PathVariable("stream") String stream) {
//        return courseService.getStream(stream);
//    }

}
