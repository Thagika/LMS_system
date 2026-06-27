package com.lms.lms_backend.Enrollment;


import com.lms.lms_backend.Enrollment.dto.EnrollmentResponse;
import com.lms.lms_backend.ExceptionHandler.ResourceNotFoundException;
import com.lms.lms_backend.assignment.LecturerCourse;
import com.lms.lms_backend.assignment.LecturerCourseService;
import com.lms.lms_backend.assignment.dto.LecturerCourseUpdateRequest;
import com.lms.lms_backend.user.User;
import com.lms.lms_backend.user.UserAdminService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository repository;
    private final EnrollmentMapper enrollmentMapper;
    private final LecturerCourseService lecturerCourseService;
    private final UserAdminService userService;

    @Override
    public @Nullable List<EnrollmentResponse> findAllEnrolledCourses(String studentEmail) {
        return Objects.requireNonNull(repository.findByStudentEmail(studentEmail))
                .stream()
                .map(enrollmentMapper::mapToResponse)
                .toList();
    }

    @Override
    @Transactional
    public void enrollment(LecturerCourseUpdateRequest request, String confirmed, String studentEmail) {
        // 1. Validate the confirmed string is not null/blank
        if (confirmed == null || confirmed.trim().isEmpty()) {
            throw new IllegalArgumentException("Confirmation course code must be provided");
        }

        // 2. Fetch the lecturer course entity through the service boundary
        LecturerCourse lecturerCourse = lecturerCourseService.getLecturerCourseEntity(request.getLecturerCourse());

        // 3. Verify the confirmed string matches the actual course code
        String actualCourseCode = lecturerCourse.getCourse().getCode();
        if (!confirmed.trim().equalsIgnoreCase(actualCourseCode)) {
            throw new IllegalArgumentException("Confirmation code does not match the course code");
        }

        // 4. Fetch the student entity through the service boundary
        User student = userService.getUserByEmail(studentEmail);

        // 5. Check if an enrollment already exists for this student and lecturer course
        Optional<Enrollment> existingEnrollment = repository.findByStudentEmailAndLecturerCourseId(
                studentEmail, lecturerCourse.getId());

        if (existingEnrollment.isPresent()) {
            Enrollment enrollment = existingEnrollment.get();
            if (enrollment.isActive()) {
                // Already actively enrolled
                throw new IllegalStateException("Student is already enrolled in this course");
            }
            // Re-activate the inactive enrollment
            enrollment.setActive(true);
            repository.save(enrollment);
        } else {
            // Create a new enrollment record
            Enrollment newEnrollment = Enrollment.builder()
                    .lecturer_course(lecturerCourse)
                    .student(student)
                    .isActive(true)
                    .build();
            repository.save(newEnrollment);
        }
    }

    @Override
    @Transactional
    public void Withdrawal(boolean confirmed, String studentEmail) {
        if (!confirmed) {
            throw new IllegalArgumentException("Withdrawal must be confirmed");
        }

        // Find all active enrollments for the student
        List<Enrollment> activeEnrollments = repository.findByStudentEmailAndIsActiveTrue(studentEmail);

        if (activeEnrollments.isEmpty()) {
            throw new ResourceNotFoundException("No active enrollments found for this student");
        }

        // Set each active enrollment to inactive
        for (Enrollment enrollment : activeEnrollments) {
            enrollment.setActive(false);
        }
        repository.saveAll(activeEnrollments);
    }
}
