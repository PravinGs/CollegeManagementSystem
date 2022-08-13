package com.example.CollegeManagement.Service;

import com.example.CollegeManagement.Dto.FeesStructureDto;
import com.example.CollegeManagement.Dto.PaymentDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;

@Service
public interface FeesStructureService {
    ResponseEntity<?> register(FeesStructureDto dto);
    ResponseEntity<?> update(Long id, FeesStructureDto dto);
//    void payment(PaymentDto dto);
}
