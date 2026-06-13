package com.lms.lms_backend.assignment;

import com.lms.lms_backend.assignment.dto.LecturerCourseRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LecturerCourseRepository extends JpaRepository<LecturerCourse , Integer> {
    List<LecturerCourseRequest> findAllByIsActiveTrue();

    List<LecturerCourseRequest> findAllByIsActiveFalse();

    Optional<LecturerCourse> findByIdAndIsActiveTrue(Integer id);
    @Modifying
    @Query("UPDATE LecturerCourse lc SET lc.isActive = false WHERE lc.course.id = :courseId AND lc.isActive = true")
    void deactivateByCourseId(Integer courseId);


    boolean existsByLecturerIdAndCourseId(UUID id, Integer id1);

    List<LecturerCourseRequest> findByCourseCodeContainingIgnoreCase(String keyword);
}
