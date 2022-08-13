package com.example.CollegeManagement.Repository;

import com.example.CollegeManagement.Entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query(
            nativeQuery = true,
            value = "select * from college.course as c where c.stream_id = :stream"
    )
    Course findByStreamId(@Param("stream") String stream);

    boolean existsByStreamId(String streamId);
}
