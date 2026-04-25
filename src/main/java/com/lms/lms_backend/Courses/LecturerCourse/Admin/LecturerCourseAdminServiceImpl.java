package com.lms.lms_backend.Courses.LecturerCourse.Admin;

import com.lms.lms_backend.Courses.Admin.AssignRequest;
import com.lms.lms_backend.Courses.Course;
import com.lms.lms_backend.Courses.CourseRepository;
import com.lms.lms_backend.Courses.LecturerCourse.*;
import com.lms.lms_backend.ExceptionHandler.ResourceNotFoundException;
import com.lms.lms_backend.user.Role;
import com.lms.lms_backend.user.User;
import com.lms.lms_backend.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class LecturerCourseAdminServiceImpl extends LecturerCourseServiceImpl implements LecturerCourseAdminService {

    public LecturerCourseAdminServiceImpl(LecturerCourseRepository lecturerCourseRepository, CourseRepository repository, UserRepository userRepository) {
        super(lecturerCourseRepository);
        this.repository = repository;
        this.userRepository = userRepository;
    }

    private final CourseRepository repository;
    private final UserRepository userRepository;





    @Override
    public List<LecturerCourseResponse> getAllInactiveAssignedCourseLecturers() {
        return lecturerCourseRepository.findAllByIsActiveFalse()
                .stream()
                .map(this::mapToAssignmentResponse)
                .toList();
    }



    @Override
    @Transactional
    public void assignExistingLecturerToExistingCourse(AssignRequest request) {
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
        if (lecturerCourseRepository.existsByLecturerIdAndCourseId(user.getId(), course.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This lecturer is already assigned to this course check inactive data");
        }
        LecturerCourse assignment = LecturerCourse.builder()
                .lecturer(user)
                .course(course)
                .isActive(true)
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
                .orElseThrow(() -> new ResourceNotFoundException("Course assigned to lecturer not found"));

        lecturerCourse.setActive(false);
    }

    @Override
    @Transactional
    public void reassignCourseToLecturer(Integer id) {

        LecturerCourse lecturerCourse = lecturerCourseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course assigned to lecturer not found"));
        if(!lecturerCourse.getCourse().isActive()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Course must be active");
        }
        lecturerCourse.setActive(true);
    }

    @Override
    @Transactional
    public void replaceCourseLecturer(LecturerCourseUpdateRequest request, boolean confirmed) {
        if (!confirmed) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "must be confirmed");
        }

        LecturerCourse assignment = lecturerCourseRepository.findByIdAndIsActiveTrue(request.getLecturerCourse())
                .orElseThrow(() -> new ResourceNotFoundException("Active assignment not found"));

        User newUser = userRepository.findByIdAndRoleAndIsActiveTrue(request.getLecturer(),Role.LECTURER)
                .orElseThrow(() -> new ResourceNotFoundException("User not found or a Lecturer"));

        if (lecturerCourseRepository.existsByLecturerIdAndCourseId(newUser.getId(), assignment.getCourse().getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This lecturer is already assigned to this course check inactive assigned courses");
        }
        assignment.setLecturer(newUser);
    }
}
