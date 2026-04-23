package com.lms.lms_backend.Courses.Admin;

import com.lms.lms_backend.Courses.*;
import com.lms.lms_backend.Courses.LecturerCourse.LecturerCourseRepository;
import com.lms.lms_backend.ExceptionHandler.ResourceNotFoundException;
import com.lms.lms_backend.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
public class CourseAdminServiceImpl extends CourseServiceImpl implements CourseAdminService {

    public CourseAdminServiceImpl(
            CourseRepository repository,
            LecturerCourseRepository lecturerCourseRepository,
            UserRepository userRepository) {

        super(repository);
        this.lecturerCourseRepository = lecturerCourseRepository;
    }

    private final LecturerCourseRepository lecturerCourseRepository;


    @Override
    public List<CourseResponse> getAllDisabledCourses() {
        List<Course> courses = repository.findAllByIsActiveFalse();

        return courses.stream()
                .map(this::mapToResponse)
                .toList();
    }


    @Override
    public CourseResponse createCourse(CreateRequest request) {
        Course course = Course.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .code(request.getCode())
                .credits(request.getCredits().byteValue())
                .isActive(true)
                .build();

        Course savedCourse = repository.save(course);
        return mapToResponse(savedCourse);
    }


    @Override
    @Transactional
    public CourseResponse updateCourse(Integer id, CreateRequest request) {
        // Fetch the existing entity
        Course course = repository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));

        // Update fields from the request
        if (request.getTitle() != null && !request.getTitle().isBlank()) {
            course.setTitle(request.getTitle());
        }

        if (request.getDescription() != null) {
            course.setDescription(request.getDescription());
        }

        if (request.getCode() != null && !request.getCode().isBlank()) {
            course.setCode(request.getCode());
        }

        if (request.getCredits() != null) {
            course.setCredits(request.getCredits().byteValue());
        }

        // 3. Save the modified entity
        Course updatedCourse = repository.save(course);
        return mapToResponse(updatedCourse);
    }


    @Override
    @Transactional
    public void disableCourse(Integer id, boolean confirmed) {
        if (!confirmed) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Deletion must be confirmed");
        }

        Course course = repository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        course.setActive(false);
        lecturerCourseRepository.deactivateByCourseId(id);
    }

    @Override
    public void enableCourse(Integer id) {

        Course course = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        course.setActive(true);
    }
}
