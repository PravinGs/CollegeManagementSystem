package com.example.CollegeManagement.Dto;


import java.io.Serializable;
import java.util.Date;

public class ExamDto implements Serializable {
    private String examName;
    private Date date;
    private String streamId;

    public String getExamName() {
        return examName;
    }

    public Date getDate() {
        return date;
    }

    public String getStreamId() {
        return streamId;
    }
}
