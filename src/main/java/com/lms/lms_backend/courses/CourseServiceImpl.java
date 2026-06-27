package com.lms.lms_backend.courses;

import com.lms.lms_backend.ExceptionHandler.ResourceNotFoundException;
import com.lms.lms_backend.courses.dto.CourseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository repository;
    private final CourseMapper courseMapper;

    @Override
    public List<CourseResponse> getAllCourses() {
        List<Course> courses = repository.findAllByIsActiveTrue();

        return courses.stream()
                .map(courseMapper::mapToResponse)
                .toList();
    }

    @Override
    public CourseResponse getCourseById(Integer id) {
        return repository.findByIdAndIsActiveTrue(id)
                .map(courseMapper::mapToResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
    }

    @Override
    public Course getActiveCourseEntity(Integer id) {
        return repository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Active course not found with id: " + id));
    }
}
