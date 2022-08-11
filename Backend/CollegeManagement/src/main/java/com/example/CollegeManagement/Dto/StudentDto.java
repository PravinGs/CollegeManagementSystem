package com.example.CollegeManagement.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
public class StudentDto {
    private final String name;
    private final String registerNo;
    private final String fatherName;
    private final Date birthDate;
    private final String email;
    private final String mobile;
    private final String gender;
    private final String course;
    private final Integer year;
    private final String nativePlace;
    private final String photo;
}
