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
public class FeesStructure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Float tuitionFees;
    private Float hostelFees;
    private Float otherFees;
    private boolean scholarShip;
    private Float scholarShipFees;
    private Float totalFees;
    @OneToOne
    @JoinColumn(
            name = "student_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_FEES_STRUCTURE_STUDENT")
    )
    private Student student;
    private Date date;
}
