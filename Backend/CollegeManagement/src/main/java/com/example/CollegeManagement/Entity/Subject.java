package com.example.CollegeManagement.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@SuppressWarnings("com.haulmont.jpb.LombokDataInspection")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String subjectName;
    private String subjectId;
    @ManyToOne
    @JoinColumn(
            name = "course_id",
            foreignKey = @ForeignKey(name = "COURSE_FK_SUBJECT")
    )
    private Course course;


}
