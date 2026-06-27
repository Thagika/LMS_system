package com.lms.lms_backend.Enrollment;


import com.lms.lms_backend.Enrollment.dto.EnrollmentProjection;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface EnrollmentRepository extends JpaRepository<Enrollment , Integer> {

    @Nullable List<EnrollmentProjection> findByStudentEmail(String studentEmail);

    @Query("SELECT e FROM Enrollment e WHERE e.student.email = :email AND e.lecturer_course.id = :lecturerCourseId")
    Optional<Enrollment> findByStudentEmailAndLecturerCourseId(
        @Param("email") String email, 
        @Param("lecturerCourseId") Integer lecturerCourseId
    );

    @Query("SELECT e FROM Enrollment e WHERE e.student.email = :email AND e.isActive = true")
    List<Enrollment> findByStudentEmailAndIsActiveTrue(@Param("email") String email);
}
