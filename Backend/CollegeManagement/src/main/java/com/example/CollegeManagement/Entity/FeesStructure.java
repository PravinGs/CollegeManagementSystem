package com.example.CollegeManagement.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@SuppressWarnings("com.haulmont.jpb.LombokDataInspection")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeesStructure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Float tuitionFees = 0f;
    private Float hostelFees = 0f;
    private Float otherFees = 0f;
    private Float totalFees = 0f;
    @ManyToOne
    @JoinColumn(
            name = "student_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_FEES_STRUCTURE_STUDENT")
    )
    private Student student;
    private Date date;
}
