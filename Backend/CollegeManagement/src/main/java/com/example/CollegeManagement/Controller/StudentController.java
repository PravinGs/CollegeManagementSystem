package com.example.CollegeManagement.Controller;

import com.example.CollegeManagement.Config.CustomStudentAuthenticationProvider;
import com.example.CollegeManagement.Dto.*;
import com.example.CollegeManagement.Entity.Student;
import com.example.CollegeManagement.Service.StudentService;
import com.example.CollegeManagement.security.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.Optional;

@RestController
@RequestMapping("/student")
@CrossOrigin(origins = "*", exposedHeaders = "**")
@Slf4j
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private CustomStudentAuthenticationProvider customStudentAuthenticationProvider;
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody StudentDto dto) {
        if (studentService.existsByEmail(dto.getEmail())) return ResponseEntity.badRequest().body(new Message("Email Already Exists"));
        if (studentService.existsByRegisterNo(dto.getRegisterNo())) return ResponseEntity.badRequest().body(new Message("Register No Already Exists"));
        return studentService.register(dto);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto dto) {
        Authentication authentication;
        AuthenticationManager authenticationManager = new ProviderManager(customStudentAuthenticationProvider);
        ResponseEntity<?> httpResponseResponseEntity = ResponseEntity.badRequest().body("Bad Credentials");
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtUtils.generateJwtToken(authentication);
            Student student = studentService.getByEmail(authentication.getPrincipal().toString());
            httpResponseResponseEntity = ResponseEntity.ok().body(new JwtResponse(
                    token,
                    student.getId(),
                    student.getName(),
                    student.getEmail(),
                    student.getRole()
            ));
        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
        }
        return httpResponseResponseEntity;
    }
    @GetMapping("/logout")
    public ResponseEntity<?> logout(){
        System.out.println(SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            SecurityContextHolder.clearContext();
            return ResponseEntity.ok().body(new Message("You have Logged out successfully..."));
        }
        return ResponseEntity.badRequest().body(new Message("You already in the logout session..."));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody StudentDto dto, @PathVariable("id") Long id) {
        return studentService.update(id, dto);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return studentService.delete(id);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        return studentService.getById(id);
    }
    @GetMapping("/attendance/{id}")
    public ResponseEntity<?> getAttendance(@PathVariable("id") Long id) {
        return studentService.getAttendance(id);
    }
    @GetMapping("/check/{stream}")
    public ResponseEntity<?> getStudents(@PathVariable("stream") String stream) {
        return studentService.getStudentsByStream(stream);
    }
    @GetMapping("/fees/{id}")
    public ResponseEntity<?> getFeesStructure(@PathVariable("id") Long id) {
        return studentService.getFeesStructure(id);
    }
    @PostMapping("/payment")
    public ResponseEntity<?> payment( @RequestBody PaymentDto dto) {
        return studentService.payment(dto);
    }



}
