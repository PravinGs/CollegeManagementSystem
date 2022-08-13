package com.example.CollegeManagement.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.util.Date;


@SuppressWarnings("com.haulmont.jpb.LombokDataInspection")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"email", "registerNo"}))
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private String registerNo;
    @NonNull
    private String fatherName;
    @NonNull
    private Date birthDate;
    @NonNull
    private String email;
    @NonNull
    private String mobile;
    @NonNull
    private String gender;
    @NonNull
    private String course;
    @NonNull
    private Integer year;
    @NonNull
    private String password;
    @NonNull
    private String nativePlace;
    @NonNull
    private String photo;
    private String role = "ROLE_STUDENT";

}