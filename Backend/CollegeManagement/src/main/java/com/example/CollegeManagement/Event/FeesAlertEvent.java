package com.example.CollegeManagement.Event;


import org.springframework.context.ApplicationEvent;

import java.util.List;

public class FeesAlertEvent extends ApplicationEvent {
    private final List<String> emails;
    public FeesAlertEvent(Object source, List<String> emails) {
        super(source);
        this.emails = emails;
    }

    public List<String> getEmails() {
        return emails;
    }
}
