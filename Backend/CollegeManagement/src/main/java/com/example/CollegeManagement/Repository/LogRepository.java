package com.example.CollegeManagement.Repository;

import com.example.CollegeManagement.Entity.Log;
import com.example.CollegeManagement.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LogRepository extends JpaRepository<Log, Long> {
    @Query(
            nativeQuery = true,
            value = "select workingDays / presentDays from log as l where student_id = :s_id"
    )
    float getAttendance(@Param("s_id") Long student_id);
    boolean existsByStudent(Student student);
    void deleteByStudent(Student student);
    Log findByStudent(Student student);
    @Modifying
    @Query(
            nativeQuery = true,
            value = "update college.log as l set l.present_days = l.present_days+1 where l.student_id = :id"
    )
    void updateAttendance(@Param("id") Long id);
    @Modifying
    @Query(
            nativeQuery = true,
            value = "update college.log as l set l.working_days = l.working_days + 1 where l.id > 0"
    )
    void updateWorkingDay();

    @Query(
            nativeQuery = true,
            value = "select * from college.log as l where l.student_id = :id"
    )
    Log findByStudentId(@Param("id") Long id);

}
