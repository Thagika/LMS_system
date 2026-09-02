package com.lms.lms_backend.enrollment;

import com.lms.lms_backend.enrollment.dto.EnrollmentResponse;
import com.lms.lms_backend.assignment.dto.LecturerCourseUpdateRequest;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface EnrollmentService {

    @Nullable List<EnrollmentResponse> findAllEnrolledCourses(String studentEmail);

    //need to take into account what if the student is enrolling again
    void enrollment(LecturerCourseUpdateRequest request, String confirmed, String studentEmail);

    void Withdrawal(boolean confirmed, String studentEmail);

}
