package com.lms.lms_backend.Courses.LecturerCourse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LecturerCourseUpdateRequest {
    Integer LecturerCourse;
    UUID lecturer;
}
