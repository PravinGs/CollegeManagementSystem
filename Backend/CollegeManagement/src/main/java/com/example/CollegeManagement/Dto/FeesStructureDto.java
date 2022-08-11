package com.example.CollegeManagement.Dto;

import lombok.Data;

@Data
public class FeesStructureDto {
    private Long id;
    private Float tuitionFees;
    private Float hostelFees;
    private Float otherFees;
    private boolean scholarShip;
    private Float scholarShipFees;
    private Float totalFees;
    private Float paidFees = 0.0f;
}
