package com.example.CollegeManagement.Dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class MarkDto implements Serializable {
    private Long studentId;
    private Long examId;
    private int mark1;
    private int mark2;
    private int mark3;
    private int mark4;
    private int mark5;
    private int mark6;

}
