package com.example.CollegeManagement.Controller;

import com.example.CollegeManagement.Dto.FeesStructureDto;
import com.example.CollegeManagement.Dto.PaymentDto;
import com.example.CollegeManagement.Service.FeesStructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;

@RestController
@RequestMapping("/fees")
public class FeesStructureController {
    @Autowired
    private FeesStructureService feesStructureService;

    @PostMapping("/register")
    public ResponseEntity<HttpResponse> register(@RequestBody FeesStructureDto dto) {
        return feesStructureService.register(dto);
    }
    @PostMapping("/payment")
    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    public ResponseEntity<HttpResponse> payment(@RequestBody PaymentDto dto) {
        ResponseEntity<HttpResponse> responseResponseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        try {
            feesStructureService.payment(dto);
            responseResponseEntity = new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseResponseEntity;
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<HttpResponse> update(@RequestBody FeesStructureDto dto, @PathVariable("id") Long id) {
        return feesStructureService.update(id, dto);
    }
}
