package com.example.CollegeManagement.Repository;

import com.example.CollegeManagement.Entity.Attendance;
import com.example.CollegeManagement.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    @Query(
            nativeQuery = true,
            value = "select * from college.attendance as a where a.student_id = :studentId and a.date = :date"
    )
    Attendance getByIdAndDate(@Param("studentId") Long studentId, @Param("date") Date date);

    @Query(
            nativeQuery = true,
            value = "select name from college.student as s left join college.attendance as a on s.id = a.student_id where a.date = :date"
    )
    List<String> getNameByDate(@Param("date") Date date);
    @Query(
            nativeQuery = true,
            value = "select 1 from college.attendance as a where a.student_id = :id"
    )
    int existsByStudentId(@Param("id") Long id);
    @Modifying
    @Query(
            nativeQuery = true,
            value = "delete from college.attendance  where student_id = :id "
    )
    void deleteByStudentId(@Param("id") Long id);
}
