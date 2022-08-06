package com.example.CollegeManagement.Service;

import com.example.CollegeManagement.Dto.FeesStructureDto;
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
    public ResponseEntity<HttpResponse> register(FeesStructureDto dto) {
        Optional<Student> s = studentRepository.findById(dto.getId());
        Log log = new Log();
        if (s.isPresent()) {
            Student st = s.get();
            FeesStructure fs = new FeesStructure();
            fs.setStudent(s.get());
            fs.setTotalFees(dto.getTotalFees());
            fs.setTuitionFees(dto.getTuitionFees());
            fs.setHostelFees(dto.getHostelFees());
            fs.setOtherFees(dto.getOtherFees());
            fs.setScholarShip(dto.isScholarShip());
            fs.setScholarShipFees(dto.getScholarShipFees());
            fs.setDate(new Date());
            studentRepository.save(st);
            // Log Table Creation / Updation
            log.setStudent(s.get());
            log.setTotalFees(dto.getTotalFees());
            log.setHostelFees(dto.getHostelFees());
            log.setOtherFees(dto.getOtherFees());
            log.setTuitionFees(dto.getTuitionFees());
            log.setScholarFees(dto.getScholarShipFees());
            logRepository.save(log);
            feesStructureRepository.save(fs);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @Override
    public ResponseEntity<HttpResponse> update(Long id, FeesStructureDto dto) {
        Optional<Student> s = studentRepository.findById(dto.getId());
        Optional<FeesStructure> f = feesStructureRepository.findById(id);
        if (s.isPresent() && f.isPresent()) {
            FeesStructure fs = f.get();
            if (dto.getTuitionFees() > -1) fs.setTuitionFees(dto.getTuitionFees());
            if (dto.getHostelFees() > -1) fs.setHostelFees(dto.getHostelFees());
            if (dto.getOtherFees() > -1) fs.setOtherFees(dto.getOtherFees());
            if (dto.getScholarShipFees() > -1) fs.setScholarShipFees(dto.getScholarShipFees());
            if (dto.getTotalFees() > -1) fs.setTotalFees(dto.getTotalFees());
            feesStructureRepository.save(fs);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @Override
    public ResponseEntity<HttpResponse> payment(PaymentDto dto) {
        Optional<Student> s = studentRepository.findById(dto.getStudentId());
        if (s.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        FeesStructure f = feesStructureRepository.findByStudent(s.get());
        if (dto.getOtherFees() > 0) f.setOtherFees(f.getOtherFees() - dto.getOtherFees());
        if (dto.getHostelFees() > 0) f.setHostelFees(f.getHostelFees() - dto.getHostelFees());
        if (dto.getTuitionFees() > 0) f.setTuitionFees(f.getTuitionFees() - dto.getTuitionFees());
        feesStructureRepository.save(f);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}


//            String fees = "TuitionFees : " + dto.getTuitionFees() + ", " +
//                    "HostelFees : " + dto.getHostelFees() + ", " +
//                    "OtherFees : " + dto.getOtherFees() + ", " +
//                    "ScholarShipFees : " + dto.getScholarShipFees() + ", " +
//                    "TotalFees : " + dto.getTotalFees();
//            st.setFeesStructure(fees);