package com.lms.lms_backend.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LecturerCourseResponse {
    private Integer id;
    private String lecturerFirstName;
    private String lecturerLastName;
    private String courseTitle;
    private String courseCode;
}

