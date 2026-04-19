package com.lms.lms_backend.Courses.Admin;

import com.lms.lms_backend.Courses.*;
import com.lms.lms_backend.Courses.LecturerCourse.LecturerCourse;
import com.lms.lms_backend.Courses.LecturerCourse.LecturerCourseRepository;
import com.lms.lms_backend.user.Role;
import com.lms.lms_backend.user.User;
import com.lms.lms_backend.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


@Service
public class CourseAdminServiceImpl extends CourseServiceImpl implements CourseAdminService {

    public CourseAdminServiceImpl(
            CourseRepository repository,
            LecturerCourseRepository lecturerCourseRepository,
            UserRepository userRepository) {

        super(repository);
        this.lecturerCourseRepository = lecturerCourseRepository;
        this.userRepository = userRepository;
    }

    private final LecturerCourseRepository lecturerCourseRepository;
    private final UserRepository userRepository;


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
    public void deleteCourse(Integer id, boolean confirmed) {
        if (!confirmed) {
            throw new IllegalArgumentException("Action must be explicitly confirmed.");
        }

        Course course = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));

        course.setActive(false);
        repository.save(course);
    }

    @Override
    @Transactional
    public void assignExistingLectuererExistingCourse(AssignRequest request) {
        // Fetch only if active
        Course course = repository.findByIdAndIsActiveTrue(request.getCourse())
                .orElseThrow(() -> new RuntimeException("Active course not found with id: " + request.getCourse()));

        //Fetch the user(no role validation) from the user table
        User user = userRepository.findById(request.getLecturer())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + request.getLecturer()));

        //Validate Role
        if (user.getRole() != Role.LECTURER) {
            throw new RuntimeException("User must be a LECTURER to be assigned to a course");
        }

        // is the course already assigned to him
        if (lecturerCourseRepository.existsByLecturerIdAndCourseId(user.getId(), course.getId())) {
            throw new RuntimeException("This lecturer is already assigned to this course");
        }

        LecturerCourse assignment = LecturerCourse.builder()
                .lecturer(user)
                .course(course)
                .build();

        lecturerCourseRepository.save(assignment);
    }
}
