package com.example.CollegeManagement.Dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SubjectDto implements Serializable {
    private String courseId;
    private String subjectId;
    private String subjectName;
}
