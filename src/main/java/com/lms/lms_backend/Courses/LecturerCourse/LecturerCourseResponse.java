package com.lms.lms_backend.Courses.LecturerCourse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LecturerCourseResponse {
    private Integer id;
    private String lecturerFirstName;
    private String lecturerLastName;
    private String courseTitle;
    private String courseCode;
}

