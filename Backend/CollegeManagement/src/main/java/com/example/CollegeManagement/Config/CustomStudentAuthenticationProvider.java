package com.example.CollegeManagement.Config;

import com.example.CollegeManagement.Entity.Student;
import com.example.CollegeManagement.Repository.StudentRepository;
import com.example.CollegeManagement.Service.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomStudentAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private StudentServiceImpl service;
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        Student student = studentRepository.findByEmail(email);
        if (student == null) throw new UsernameNotFoundException("Username not found.");
        if (passwordEncoder.matches(password, student.getPassword())) {
            List<GrantedAuthority> list = new ArrayList<>();
            list.add(new SimpleGrantedAuthority(student.getRole()));
            return new UsernamePasswordAuthenticationToken(email, password, list);
        } else {
            return (Authentication) new BadCredentialsException("Invalid Credentials");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
