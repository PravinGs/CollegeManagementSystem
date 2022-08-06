package com.example.CollegeManagement.Controller;

import com.example.CollegeManagement.Config.CustomStudentAuthenticationProvider;
import com.example.CollegeManagement.Dto.LoginDto;
import com.example.CollegeManagement.Dto.StudentDto;
import com.example.CollegeManagement.Entity.Student;
import com.example.CollegeManagement.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;

@RestController
@RequestMapping("/student")
@RestControllerAdvice
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private CustomStudentAuthenticationProvider customStudentAuthenticationProvider;

    @PostMapping("/register")
    public Student register(@RequestBody StudentDto dto) {
        Student student = null;
        try{
            student = studentService.register(dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return student;
    }
    @PostMapping("/login")
    public ResponseEntity<HttpResponse> login(@RequestBody LoginDto dto) {
        Authentication authentication;
        AuthenticationManager authenticationManager = new ProviderManager(customStudentAuthenticationProvider);
        ResponseEntity<HttpResponse> httpResponseResponseEntity = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            httpResponseResponseEntity = new ResponseEntity<>(HttpStatus.OK);
        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
        }
        return httpResponseResponseEntity;
    }
    @GetMapping("/logout")
    public ResponseEntity<HttpResponse> logout(){
        System.out.println(SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            SecurityContextHolder.clearContext();
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<HttpResponse> update(@RequestBody StudentDto dto, @PathVariable("id") Long id) {
        return studentService.update(id, dto);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpResponse> delete(@PathVariable("id") Long id) {
        return studentService.delete(id);
    }
    @GetMapping("/get/{id}")
    public Student getById(@PathVariable("id") Long id) {
        return studentService.getById(id);
    }

}
