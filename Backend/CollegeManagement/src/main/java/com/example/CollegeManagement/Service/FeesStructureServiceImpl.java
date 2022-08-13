package com.example.CollegeManagement.Service;

import com.example.CollegeManagement.Dto.FeesStructureDto;
import com.example.CollegeManagement.Dto.Message;
import com.example.CollegeManagement.Dto.PaymentDto;
import com.example.CollegeManagement.Entity.FeesStructure;
import com.example.CollegeManagement.Entity.Log;
import com.example.CollegeManagement.Entity.Student;
import com.example.CollegeManagement.Repository.FeesStructureRepository;
import com.example.CollegeManagement.Repository.LogRepository;
import com.example.CollegeManagement.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class FeesStructureServiceImpl implements FeesStructureService{
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private FeesStructureRepository feesStructureRepository;
    @Autowired
    private LogRepository logRepository;
    @Override
    public ResponseEntity<?> register(FeesStructureDto dto) {
        Optional<Student> s = studentRepository.findById(dto.getId());
        Log log = new Log();
        if (s.isPresent()) {
            Student st = s.get();
            // Log Table Creation / Updation
            log.setStudent(s.get());
            log.setTotalFees(dto.getTotalFees());
            log.setHostelFees(dto.getHostelFees());
            log.setOtherFees(dto.getOtherFees());
            log.setTuitionFees(dto.getTuitionFees());
            log.setScholarFees(dto.getScholarShipFees());
            logRepository.save(log);
            return ResponseEntity.ok().body(new Message("Fees Structure updated.."));
        }
        return ResponseEntity.badRequest().body(new Message("No student exists with this id"));
    }
    @Override
    public ResponseEntity<?> update(Long id, FeesStructureDto dto) {
        Optional<Student> s = studentRepository.findById(dto.getId());
        Optional<Log> f = logRepository.findById(id);
        if (s.isPresent() && f.isPresent()) {
            Log fs = f.get();
            if (dto.getTuitionFees() > -1) fs.setTuitionFees(dto.getTuitionFees());
            if (dto.getHostelFees() > -1) fs.setHostelFees(dto.getHostelFees());
            if (dto.getOtherFees() > -1) fs.setOtherFees(dto.getOtherFees());
            if (dto.getScholarShipFees() > -1) fs.setScholarFees(dto.getScholarShipFees());
            if (dto.getTotalFees() > -1) fs.setTotalFees(dto.getTotalFees());
            logRepository.save(fs);
            return ResponseEntity.ok().body(new Message("Fees details updates"));
        }
        return ResponseEntity.badRequest().body(new Message("Check the details no student exists with this id"));
    }
//    @Override
//    public void payment(PaymentDto dto) {
//        Optional<Student> s = studentRepository.findById(dto.getStudentId());
//        if (s.isEmpty()) {
//            new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            return;
//        }
//        FeesStructure f = feesStructureRepository.findByStudent(s.get());
//        if (dto.getOtherFees() > 0) f.setOtherFees(f.getOtherFees() - dto.getOtherFees());
//        if (dto.getHostelFees() > 0) f.setHostelFees(f.getHostelFees() - dto.getHostelFees());
//        if (dto.getTuitionFees() > 0) f.setTuitionFees(f.getTuitionFees() - dto.getTuitionFees());
//        feesStructureRepository.save(f);
//        new ResponseEntity<>(HttpStatus.OK);
//    }
}


//            String fees = "TuitionFees : " + dto.getTuitionFees() + ", " +
//                    "HostelFees : " + dto.getHostelFees() + ", " +
//                    "OtherFees : " + dto.getOtherFees() + ", " +
//                    "ScholarShipFees : " + dto.getScholarShipFees() + ", " +
//                    "TotalFees : " + dto.getTotalFees();
//            st.setFeesStructure(fees);