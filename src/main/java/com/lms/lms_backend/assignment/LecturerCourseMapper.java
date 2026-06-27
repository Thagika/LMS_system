package com.lms.lms_backend.assignment;

import com.lms.lms_backend.assignment.dto.LecturerCourseProjection;
import com.lms.lms_backend.assignment.dto.LecturerCourseResponse;
import org.springframework.stereotype.Component;

@Component
public class LecturerCourseMapper {

    public LecturerCourseResponse mapToAssignmentResponse(LecturerCourseProjection projection) {
        return LecturerCourseResponse.builder()
                .id(projection.getId())
                .lecturerFirstName(projection.getLecturerFirstName()) // No nested .getLecturer() call needed!
                .lecturerLastName(projection.getLecturerLastName())
                .courseTitle(projection.getCourseTitle())
                .courseCode(projection.getCourseCode())
                .build();
    }
}
