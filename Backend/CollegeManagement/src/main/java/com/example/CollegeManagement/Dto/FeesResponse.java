package com.example.CollegeManagement.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FeesResponse {
    private float tuitionFees;
    private float scholarFees;
    private float hostelFees;
    private float otherFees;
    private float totalFees;

}
