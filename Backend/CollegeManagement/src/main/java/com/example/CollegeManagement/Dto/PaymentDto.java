package com.example.CollegeManagement.Dto;
import lombok.Data;
@Data
public class PaymentDto {
    private Long studentId;
    private Float tuitionFees = 0.0f;
    private Float hostelFees = 0.0f;
    private Float otherFees = 0.0f;
}
