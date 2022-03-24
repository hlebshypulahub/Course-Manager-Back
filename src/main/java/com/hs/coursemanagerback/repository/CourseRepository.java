package com.hs.coursemanagerback.repository;

import com.hs.coursemanagerback.model.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAllByEmployeeId(Long employeeId);
}
