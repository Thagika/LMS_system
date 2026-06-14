package com.lms.lms_backend.courses;

import com.lms.lms_backend.courses.dto.CourseResponse;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {
        public CourseResponse mapToResponse(Course course) {
            return CourseResponse.builder()
                    .id(course.getId())
                    .title(course.getTitle())
                    .description(course.getDescription())
                    .code(course.getCode())
                    .credits(course.getCredits())
                    .build();
        }
}
