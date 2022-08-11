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
public class Mark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(
            name = "exam_id",
            foreignKey = @ForeignKey(name = "EXAM_MARK_FK_STUDENT")
    )
    private Exam exam;

    @ManyToOne
    @JoinColumn(
            name = "student_id",
            foreignKey = @ForeignKey(name = "STUDENT_FK_MARK")
    )
    private Student student;
    private int mark1;
    private int mark2;
    private int mark3;
    private int mark4;
    private int mark5;
    private int mark6;
    private Date date;
}
