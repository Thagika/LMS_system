package com.lms.lms_backend.Courses.LecturerCourse.Admin;

import com.lms.lms_backend.Courses.Admin.AssignRequest;
import com.lms.lms_backend.Courses.Course;
import com.lms.lms_backend.Courses.CourseRepository;
import com.lms.lms_backend.Courses.CourseServiceImpl;
import com.lms.lms_backend.Courses.LecturerCourse.LecturerCourse;
import com.lms.lms_backend.Courses.LecturerCourse.LecturerCourseRepository;
import com.lms.lms_backend.Courses.LecturerCourse.LecturerCourseResponse;
import com.lms.lms_backend.Courses.LecturerCourse.LecturerCourseUpdateRequest;
import com.lms.lms_backend.ExceptionHandler.ResourceNotFoundException;
import com.lms.lms_backend.user.Role;
import com.lms.lms_backend.user.User;
import com.lms.lms_backend.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public class LectuerCourseAdminServiceImpl extends CourseServiceImpl implements LectuerCourseAdminService {


    public LectuerCourseAdminServiceImpl(CourseRepository repository, UserRepository userRepository, LecturerCourseRepository lecturerCourseRepository) {
        super(repository);
        this.userRepository = userRepository;
        this.lecturerCourseRepository = lecturerCourseRepository;
    }

    private final UserRepository userRepository;
    private final LecturerCourseRepository lecturerCourseRepository;

    @Override
    public List<LecturerCourseResponse> getAllActiveAssignedCourseLectuerers() {
        return lecturerCourseRepository.findAllByIsActiveTrue()
                .stream()
                .map(this::mapToAssignmentResponse) // Use your new DTO mapping logic
                .toList();
    }

    @Override
    public List<LecturerCourseResponse> getAllInactiveAssignedCourseLectuerers() {
        return lecturerCourseRepository.findAllByIsActiveFalse()
                .stream()
                .map(this::mapToAssignmentResponse)
                .toList();
    }

    private LecturerCourseResponse mapToAssignmentResponse(LecturerCourse entity) {
        return LecturerCourseResponse.builder()
                .id(entity.getId())
                .lecturerFirstName(entity.getLecturer().getFirstName())
                .lecturerLastName(entity.getLecturer().getLastName())
                .courseTitle(entity.getCourse().getTitle())
                .courseCode(entity.getCourse().getCode())
                .build();
    }

    @Override
    @Transactional
    public void assignExistingLectuererExistingCourse(AssignRequest request) {
        // Fetch only if active
        Course course = repository.findByIdAndIsActiveTrue(request.getCourse())
                .orElseThrow(() -> new ResourceNotFoundException("Active course not found with id: " + request.getCourse()));

        User user = userRepository.findByIdAndIsActiveTrue(request.getLecturer())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.getLecturer()));

        //Validate Role
        if (user.getRole() != Role.LECTURER) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User must be a LECTURER");
        }

        // is the course already assigned to him
        if (lecturerCourseRepository.existsByLecturerIdAndCourseIdAndIsActiveTrue(user.getId(), course.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This lecturer is already assigned to this course");
        }
        LecturerCourse assignment = LecturerCourse.builder()
                .lecturer(user)
                .course(course)
                .build();

        lecturerCourseRepository.save(assignment);
    }

    @Override
    @Transactional
    public void disableAssignedCourse(Integer id, boolean confirmed) {
        if (!confirmed) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Deletion must be confirmed");
        }

        LecturerCourse lecturerCourse = lecturerCourseRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course assigned to lectuerer not found"));

        lecturerCourse.setActive(false);
    }

    @Override
    public void reassignCourseToLectuerer(Integer id) {
        LecturerCourse lecturerCourse = lecturerCourseRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course assigned to lectuerer not found"));

        lecturerCourse.setActive(true);

    }

    @Override
    @Transactional
    public void replaceCourseLectuerer(LecturerCourseUpdateRequest request, boolean confirmed) {
        if (!confirmed) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "must be confirmed");
        }
        LecturerCourse assignment = lecturerCourseRepository.findByIdAndIsActiveTrue(request.getLecturerCourse())
                .orElseThrow(() -> new ResourceNotFoundException("Active assignment not found"));

        User newUser = userRepository.findByIdAndIsActiveTrue(request.getLecturer())
                .orElseThrow(() -> new ResourceNotFoundException("Lecturer not found"));

        if (newUser.getRole() != Role.LECTURER) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User must be a LECTURER");
        }
        if (assignment.getLecturer().getId().equals(newUser.getId())) {
            return;
        }
        if (lecturerCourseRepository.existsByLecturerIdAndCourseIdAndIsActiveTrue(newUser.getId(), assignment.getCourse().getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This lecturer is already assigned to this course");
        }
        assignment.setLecturer(newUser);
    }
}
