package com.lms.lms_backend.assignment;

import com.lms.lms_backend.assignment.dto.LecturerCourseResponse;
import com.lms.lms_backend.assignment.dto.LecturerCourseUpdateRequest;
import com.lms.lms_backend.courses.dto.AssignRequest;
import com.lms.lms_backend.courses.Course;
import com.lms.lms_backend.courses.CourseRepository;
import com.lms.lms_backend.ExceptionHandler.ResourceNotFoundException;
import com.lms.lms_backend.user.Role;
import com.lms.lms_backend.user.User;
import com.lms.lms_backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LecturerCourseAdminServiceImpl implements LecturerCourseAdminService {


    private final LecturerCourseRepository lecturerCourseRepository;
    private final CourseRepository repository;
    private final UserRepository userRepository;

    private final LecturerCourseMapper courseMapper;

    @Override
    public List<LecturerCourseResponse> getAllActiveAssignedCourseLecturers() {
        return lecturerCourseRepository.findAllByIsActiveTrue()
                .stream()
                .map(courseMapper::mapToAssignmentResponse) // Use your new DTO mapping logic(mapToAssignmentResponse)
                .toList();
    }


    @Override
    public List<LecturerCourseResponse> getAllInactiveAssignedCourseLecturers() {
        return lecturerCourseRepository.findAllByIsActiveFalse()
                .stream()
                .map(courseMapper::mapToAssignmentResponse)
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
