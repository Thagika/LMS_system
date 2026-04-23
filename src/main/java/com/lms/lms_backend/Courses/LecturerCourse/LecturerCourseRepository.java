package com.lms.lms_backend.Courses.LecturerCourse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LecturerCourseRepository extends JpaRepository<LecturerCourse , Integer> {
    List<LecturerCourse> findAllByIsActiveTrue();

    List<LecturerCourse> findAllByIsActiveFalse();

    Optional<LecturerCourse> findByIdAndIsActiveTrue(Integer id);
    @Modifying
    @Query("UPDATE LecturerCourse lc SET lc.isActive = false WHERE lc.course.id = :courseId AND lc.isActive = true")
    void deactivateByCourseId(Integer courseId);

    boolean existsByLecturerIdAndCourseIdAndIsActiveTrue(UUID id, Integer id1);
}
