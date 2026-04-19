package com.lms.lms_backend.Courses.LecturerCourse;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LecturerCourseRepository extends JpaRepository<LecturerCourse , Integer> {
    List<LecturerCourse> findByCourseId(Integer courseId);

    List<LecturerCourse> findByLecturerId(UUID lecturerId);

    boolean existsByLecturerIdAndCourseId(UUID lecturerId, Integer courseId);

    // 4. Remove a lecturer from a course
    void deleteByLecturerIdAndCourseId(UUID lecturerId, Integer courseId);
}
