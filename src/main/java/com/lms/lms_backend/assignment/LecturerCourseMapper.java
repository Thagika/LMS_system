package com.lms.lms_backend.assignment;

import com.lms.lms_backend.assignment.dto.LecturerCourseRequest;
import com.lms.lms_backend.assignment.dto.LecturerCourseResponse;
import org.springframework.stereotype.Component;

@Component
public class LecturerCourseMapper {

    public LecturerCourseResponse mapToAssignmentResponse(LecturerCourseRequest projection) {
        return LecturerCourseResponse.builder()
                .id(projection.getId())
                .lecturerFirstName(projection.getLecturerFirstName()) // No nested .getLecturer() call needed!
                .lecturerLastName(projection.getLecturerLastName())
                .courseTitle(projection.getCourseTitle())
                .courseCode(projection.getCourseCode())
                .build();
    }
}
