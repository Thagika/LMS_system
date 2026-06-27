package com.lms.lms_backend.Enrollment;


import com.lms.lms_backend.Enrollment.dto.EnrollmentProjection;
import com.lms.lms_backend.Enrollment.dto.EnrollmentResponse;
import org.springframework.stereotype.Component;

@Component
public class EnrollmentMapper {

    public EnrollmentResponse mapToResponse(EnrollmentProjection projection) {
        if (projection == null) {
            return null;
        }

        return EnrollmentResponse.builder()
                .id(projection.getId())
                .lecturerFirstName(projection.getLecturerCourse_Lecturer_FirstName())
                .lecturerLastName(projection.getLecturerCourse_Lecturer_LastName())
                .courseTitle(projection.getLecturerCourse_Course_Title())
                .courseCode(projection.getLecturerCourse_Course_Code())
                .build();
    }
}