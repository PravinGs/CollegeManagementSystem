package com.example.CollegeManagement.Service;

import com.example.CollegeManagement.Dto.FeesResponse;
import com.example.CollegeManagement.Dto.Message;
import com.example.CollegeManagement.Dto.PaymentDto;
import com.example.CollegeManagement.Dto.StudentDto;
import com.example.CollegeManagement.Email.EmailSenderService;
import com.example.CollegeManagement.Entity.Course;
import com.example.CollegeManagement.Entity.FeesStructure;
import com.example.CollegeManagement.Entity.Log;
import com.example.CollegeManagement.Entity.Student;
import com.example.CollegeManagement.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private FeesStructureRepository feesStructureRepository;
    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private MarkRepository markRepository;
    @Autowired
    private LogRepository logRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private EmailSenderService emailSenderService;
    @Override
    public ResponseEntity<?> register(StudentDto dto) {
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
        studentRepository.save(student);
        return ResponseEntity.ok().body(new Message("Registered successfully..."));
    }
    @Override
    public ResponseEntity<?> update(Long id, StudentDto dto) {
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
            return ResponseEntity.ok().body(student);
        }
        return ResponseEntity.badRequest().body(new Message("Invalid Student ID!"));    }
    @Override
    public ResponseEntity<?> getById(Long id) {
        Optional<Student> s = studentRepository.findById(id);
        if (s.isPresent()) return ResponseEntity.ok().body(s.get());
        return ResponseEntity.badRequest().body(new Message("Invalid Student ID: "));
    }
    @Override
    public ResponseEntity<?> delete(Long id) {
        if (studentRepository.existsById(id)) {
            Student student = studentRepository.findById(id).get();
            System.out.println("\nStudent Exists : " + student);
            if (feesStructureRepository.existsByStudent(student)) {
                System.out.println("\nStudent exists");
                feesStructureRepository.deleteByStudent(student);
            }
            if (attendanceRepository.existsByStudents(student)) {
                System.out.println("\nAttendance exists");
                attendanceRepository.deleteByStudents(student);
            }
            if (logRepository.existsByStudent(student)) {
                System.out.println("\nLog exists");
                logRepository.deleteByStudent(student);
            }
            if (markRepository.existsByStudent(student)) {
                System.out.println("\nmark exists");
                markRepository.deleteByStudent(student);
            }
            studentRepository.deleteById(id);
            return ResponseEntity.ok().body(new Message("Student " + student.getName() +" removed successfully.."));
        }
        return ResponseEntity.ok().body(new Message("Invalid Student Id!"));
    }
    @Override
    public boolean existsByEmail(String email) {
        return studentRepository.existsByEmail(email);
    }
    @Override
    public boolean existsByRegisterNo(String regNo) {
        return studentRepository.existsByRegisterNo(regNo);
    }
    @Override
    public Student getByEmail(String email) {
        return studentRepository.findByEmail(email);
    }
    @Override
    public ResponseEntity<?> getAttendance(Long id) {
        if (studentRepository.existsById(id)) {
            Float percentage = logRepository.getAttendance(id);
            return ResponseEntity.ok().body(percentage);
        }
        return ResponseEntity.badRequest().body(new Message("Invalid student id!"));
    }
    @Override
    public ResponseEntity<?> getStudentsByStream(String stream) {
        Course course = courseRepository.findByStreamId(stream);
        System.out.println(course);
        if (course == null) {
            return ResponseEntity.badRequest().body("No stream available with this name");
        }
        return ResponseEntity.ok().body(course);
    }

    @Override
    public ResponseEntity<?> getFeesStructure(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if(student.isEmpty()) {
            return ResponseEntity.badRequest().body(new Message("Invalid Student Id"));
        }
        Log log = logRepository.findByStudent(student.get());
        return ResponseEntity.ok().body(new FeesResponse(log.getTuitionFees(), log.getScholarFees(), log.getHostelFees(), log.getOtherFees(), log.getTotalFees()));
    }

    @Override
    public ResponseEntity<?> payment(PaymentDto dto) {
        Optional<Student> s = studentRepository.findById(dto.getStudentId());
        if (s.isEmpty()) return ResponseEntity.badRequest().body(new Message(("Invalid Student id..")));
        Log structure = logRepository.findByStudent(s.get());
        FeesStructure fs = new FeesStructure();
        if (structure == null) return ResponseEntity.ok().body(new Message("Your Fees structure not yet published, Contact your class in charge"));
        if (dto.getTuitionFees() > 0 && structure.getTuitionFees() >= dto.getTuitionFees()) {
//            structure.setTotalFees(Math.abs(structure.getTuitionFees() - dto.getTuitionFees()));
            fs.setTuitionFees(dto.getTuitionFees());
        } else if (structure.getTuitionFees() < dto.getTuitionFees()) return ResponseEntity.ok().body(new Message("Check Your Fees details before Payment , Your remain tuition Fee only : " + structure.getTuitionFees()));
        if (dto.getHostelFees() > 0 && structure.getHostelFees() >= dto.getHostelFees()) {
//            structure.setHostelFees(Math.abs(structure.getHostelFees() - dto.getHostelFees()));
            fs.setHostelFees(dto.getHostelFees());
        }
        else if (structure.getHostelFees() < dto.getHostelFees())return ResponseEntity.ok().body(new Message("Check Your Fees details before Payment, Your remain hostel fees only :  " + structure.getHostelFees()));
        if (dto.getOtherFees() > 0 && structure.getOtherFees() >= dto.getOtherFees()) {
//            structure.setOtherFees(Math.abs(structure.getOtherFees()) - dto.getOtherFees());
            fs.setOtherFees(dto.getOtherFees());
        } else if (structure.getOtherFees() < dto.getOtherFees()) return ResponseEntity.ok().body(new Message("Check Your Fees details before Payment, Your remain other fees only :  " + structure.getOtherFees()));
        fs.setStudent(s.get());
        fs.setDate(new Date());
        feesStructureRepository.save(fs);
        sendReceipt(s.get(), dto);
        return ResponseEntity.ok().body(new Message("Payment Successful.."));
    }

    private void sendReceipt(Student student, PaymentDto dto) {
        emailSenderService.sendSimpleEmail(student.getEmail(), dto.toString(), "Payment Successful");
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Student student = studentRepository.findByEmail(email);
        System.out.println("Student : " + student + "From LoadUserByUseName");
        if (student == null) throw new UsernameNotFoundException("Student Not Found with this Email");
        return new User(student.getEmail(), student.getPassword(), new ArrayList<>());
    }
}
