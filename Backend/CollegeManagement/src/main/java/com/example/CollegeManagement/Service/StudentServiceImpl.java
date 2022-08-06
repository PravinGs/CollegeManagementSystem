package com.example.CollegeManagement.Service;

import com.example.CollegeManagement.Dto.StudentDto;
import com.example.CollegeManagement.Entity.Student;
import com.example.CollegeManagement.Repository.AttendanceRepository;
import com.example.CollegeManagement.Repository.FeesStructureRepository;
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
import java.util.Optional;

@Service
@Transactional
public class StudentServiceImpl implements StudentService, UserDetailsService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private FeesStructureRepository feesStructureRepository;
    @Autowired
    private AttendanceRepository attendanceRepository;
    @Override
    public Student register(StudentDto dto) {
        Student student = new Student();
        student.setName(dto.getName());
        student.setRegisterNo(dto.getRegisterNo());
        student.setFatherName(dto.getFatherName());
        student.setBirthDate(dto.getBirthDate());
        student.setCourse(dto.getCourse().toUpperCase());
        student.setYear(dto.getYear());
        student.setGender(dto.getGender().toUpperCase());
        student.setEmail(dto.getEmail());
        student.setNativePlace(dto.getNativePlace());
        student.setMobile(dto.getMobile());
        student.setPhoto(dto.getPhoto());
        student.setPassword(passwordEncoder.encode(dto.getRegisterNo()));
        System.out.println(student);
        return studentRepository.save(student);
    }
    @Override
    public ResponseEntity<HttpResponse> update(Long id, StudentDto dto) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            Student s = student.get();
            if (dto.getName() != null && !"".equals(dto.getName())) s.setName(dto.getName());
            if (dto.getEmail() != null && !"".equals(dto.getEmail())) s.setEmail(dto.getEmail());
            if (dto.getCourse() != null && !"".equals(dto.getCourse())) s.setCourse(dto.getCourse());
            if (dto.getGender() != null && !"".equals(dto.getGender())) s.setCourse(dto.getGender());
            if (dto.getFatherName() != null && !"".equals(dto.getFatherName())) s.setFatherName(dto.getFatherName());
            if (dto.getPhoto() != null && !"".equals(dto.getPhoto())) s.setPhoto(dto.getPhoto());
            if (dto.getBirthDate() != null) s.setBirthDate(dto.getBirthDate());
            if (dto.getYear() != null && !"".equals(dto.getYear())) s.setYear(dto.getYear());
            if (dto.getMobile() != null && !"".equals(dto.getMobile())) s.setMobile(dto.getMobile());
            if (dto.getNativePlace() != null && !"".equals(dto.getNativePlace())) s.setNativePlace((dto.getNativePlace()));
            studentRepository.save(s);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @Override
    public Student getById(Long id) {
        Optional<Student> s = studentRepository.findById(id);
        if (s.isPresent()) return s.get();
        return null;
    }
    @Override
    public ResponseEntity<HttpResponse> delete(Long id) {
        if (studentRepository.existsById(id)) {
            Student student = studentRepository.findById(id).get();
            if (feesStructureRepository.existsByStudent(student)) {
                feesStructureRepository.delete(feesStructureRepository.findByStudent(student));
            }
            if (attendanceRepository.existsByStudentId(id) == 1) {
                attendanceRepository.deleteByStudentId(id);
            }
            studentRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Student student = studentRepository.findByEmail(email);
        System.out.println("Student : " + student + "From LoadUserByUseName");
        if (student == null) throw new UsernameNotFoundException("Student Not Found with this Email");
        return new User(student.getEmail(), student.getPassword(), new ArrayList<>());
    }
}
