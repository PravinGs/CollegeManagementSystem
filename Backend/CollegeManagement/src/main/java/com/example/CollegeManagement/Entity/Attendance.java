package com.example.CollegeManagement.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    @ManyToOne
    @JoinColumn(
            name = "student_id",
            foreignKey = @ForeignKey(name = "FK_ATTENDANCE_STUDENT")
    )
    private Student students;
    private Boolean isPresent = false;
    public Attendance(Date date, Student object) {
        this.date = date;
        this.students = object;
        this.isPresent = true;
    }
}
