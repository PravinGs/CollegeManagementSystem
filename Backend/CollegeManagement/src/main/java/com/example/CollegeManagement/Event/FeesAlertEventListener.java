package com.example.CollegeManagement.Event;

import com.example.CollegeManagement.Email.EmailSenderService;
import com.example.CollegeManagement.Entity.Student;
import com.example.CollegeManagement.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;

public class FeesAlertEventListener implements ApplicationListener<FeesAlertEvent> {
    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired
    private StudentRepository studentRepository;
    @Override
    public void onApplicationEvent(FeesAlertEvent event) {

        for (String email: event.getEmails()) {
            Student student = studentRepository.findByEmail(email);
            emailSenderService.sendSimpleEmail(email, "You have Pending in your Fees.\nKindly Pay that with in 10 days.\nThis is " +
                    "your generous remainder", "Fees Payment");
        }
    }
}
