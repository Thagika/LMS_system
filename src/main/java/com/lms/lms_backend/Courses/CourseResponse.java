package com.lms.lms_backend.Courses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseResponse {
    private Integer id;
    private String title;
    private String description;
    private String code;
    private byte credits;
}
