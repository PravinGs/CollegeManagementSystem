package com.example.CollegeManagement.Dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AttendanceDto {
    private Date date;
    private List<Long> students;
}
