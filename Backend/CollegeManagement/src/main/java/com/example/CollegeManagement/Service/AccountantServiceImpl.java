package com.example.CollegeManagement.Service;

import com.example.CollegeManagement.Entity.Accountant;
import com.example.CollegeManagement.Repository.AccountantRepository;
import com.example.CollegeManagement.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AccountantServiceImpl implements AccountantService, UserDetailsService {
    @Autowired
    private AccountantRepository accountantRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private StudentRepository studentRepository;
    @Override
    public ResponseEntity<HttpResponse> register(Accountant accountant) {
        accountant.setPassword(passwordEncoder.encode(accountant.getPassword()));
        System.out.println(accountant);
        accountantRepository.save(accountant);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Override
    public List<String> getUnPaidStudents() {
        return studentRepository.getStudentEmailsByFees();
    }
    @Override
    public ResponseEntity<HttpResponse> login(Accountant accountant) {
        if (accountantRepository.existsByEmail(accountant.getEmail())) {
            Accountant accountant1 = accountantRepository.findByEmail(accountant.getEmail());
            if (accountant.getPassword().equals(accountant1.getPassword())) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @Override
    public ResponseEntity<HttpResponse> delete(Long id) {
        if (accountantRepository.existsById(id)) {
            accountantRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Accountant accountant = accountantRepository.findByEmail(email);
        if (accountant == null) throw new UsernameNotFoundException("Accountant Not Found ");
        return new User(accountant.getEmail(), accountant.getPassword(), new ArrayList<>());
    }
}
