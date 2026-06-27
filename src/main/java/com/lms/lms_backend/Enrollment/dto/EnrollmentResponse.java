package com.lms.lms_backend.Enrollment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentResponse {
    private Integer id;
    private String lecturerFirstName;
    private String lecturerLastName;
    private String courseTitle;
    private String courseCode;

}