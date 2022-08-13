package com.example.CollegeManagement.Event;

import com.example.CollegeManagement.Entity.Exam;
import org.springframework.context.ApplicationEvent;

import java.util.List;

public class ExamSendEvent extends ApplicationEvent {
    private final List<String> students;
    private final Exam exam;
    public ExamSendEvent(Exam exam, List<String> students) {
        super(exam);
        this.exam = exam;
        this.students = students;
    }

    public List<String> getStudents() {
        return students;
    }

    public Exam getExam() {
        return exam;
    }
}
