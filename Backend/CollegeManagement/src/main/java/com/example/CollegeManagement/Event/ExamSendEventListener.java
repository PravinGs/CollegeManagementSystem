package com.example.CollegeManagement.Event;

import com.example.CollegeManagement.Email.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class ExamSendEventListener implements ApplicationListener<ExamSendEvent> {

    @Autowired
    private EmailSenderService service;
    @Override
    public void onApplicationEvent(ExamSendEvent event) {
        System.out.println("\nEvent Listener getting called .");
        List<String> students = event.getStudents();
        String examName = event.getExam().getExamName();
        Date date = event.getExam().getDate();
        for (String email: students) sendSimpleMail(email,examName, date);
    }

    private void sendSimpleMail(String email, String examName, Date date) {
        service.sendSimpleEmail(email,examName,date.toString());
    }
}
