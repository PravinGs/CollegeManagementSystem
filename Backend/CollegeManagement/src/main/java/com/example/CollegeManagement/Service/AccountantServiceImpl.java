package com.example.CollegeManagement.Service;

import com.example.CollegeManagement.Dto.Message;
import com.example.CollegeManagement.Entity.Accountant;
import com.example.CollegeManagement.Repository.AccountantRepository;
import com.example.CollegeManagement.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@Transactional
public class AccountantServiceImpl implements AccountantService {
    @Autowired
    private AccountantRepository accountantRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private StudentRepository studentRepository;
    @Override
    public ResponseEntity<?> register(Accountant accountant) {
        accountant.setPassword(passwordEncoder.encode(accountant.getPassword()));
        accountantRepository.save(accountant);
        return ResponseEntity.ok().body(new Message("User Register Successfully"));

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
    public Accountant findByEmail(String email) {
        return accountantRepository.findByEmail(email);
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Accountant accountant = accountantRepository.findByEmail(email);
        if (accountant == null) throw new UsernameNotFoundException("Accountant Not Found ");
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(accountant.getRole()));
        return new User(accountant.getEmail(), accountant.getPassword(), authorities);
    }
}
