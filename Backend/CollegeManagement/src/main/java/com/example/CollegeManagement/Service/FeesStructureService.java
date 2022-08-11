package com.example.CollegeManagement.Service;

import com.example.CollegeManagement.Dto.FeesStructureDto;
import com.example.CollegeManagement.Dto.PaymentDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;

@Service
public interface FeesStructureService {
    ResponseEntity<HttpResponse> register(FeesStructureDto dto);

    ResponseEntity<HttpResponse> update(Long id, FeesStructureDto dto);

    ResponseEntity<HttpResponse> payment(PaymentDto dto);
}
