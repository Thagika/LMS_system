package com.lms.lms_backend.Courses;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course ,Integer> {
//    lists courses that are active
    List<Course> findAllByIsActiveTrue();

    // Finds by ID ONLY if isActive is true
    Optional<Course> findByIdAndIsActiveTrue(Integer id);

    List<Course> findAllByIsActiveFalse();
}