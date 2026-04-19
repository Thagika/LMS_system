package com.lms.lms_backend.Courses;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Primary
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    protected final CourseRepository repository;

    @Override
    public List<CourseResponse> getAllCourses() {
        List<Course> courses = repository.findAllByIsActiveTrue();

        return courses.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public CourseResponse getCourseById(Integer id) {
        return repository.findByIdAndIsActiveTrue(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
    }

    // Helper method to handle the conversion logic
    protected CourseResponse mapToResponse(Course course) {
        return CourseResponse.builder()
                .id(course.getId())
                .title(course.getTitle())
                .description(course.getDescription())
                .code(course.getCode())
                .credits(course.getCredits())
                .build();
    }
}
