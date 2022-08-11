package com.example.CollegeManagement.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(
            name = "student_id",
            foreignKey = @ForeignKey(name = "FK_LOG_STUDENT")
    )
    private Student student;
    private float totalFees;
    private float tuitionFees;
    private float hostelFees;
    private float otherFees;
    private float scholarFees;
    private int workingDays = 0;
    private int presentDays = 0;

}
