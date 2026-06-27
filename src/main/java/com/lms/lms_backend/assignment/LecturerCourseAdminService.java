package com.lms.lms_backend.assignment;

import com.lms.lms_backend.assignment.dto.LecturerCourseResponse;
import com.lms.lms_backend.assignment.dto.LecturerCourseUpdateRequest;
import com.lms.lms_backend.courses.dto.AssignRequest;

import java.util.List;

public interface LecturerCourseAdminService {

    List<LecturerCourseResponse> getAllActiveAssignedCourseLecturers();

    List<LecturerCourseResponse> getAllInactiveAssignedCourseLecturers();

    void assignExistingLecturerToExistingCourse(AssignRequest request);

    void disableAssignedCourse(Integer id, boolean confirmed);

    void reassignCourseToLecturer(Integer id);

    void replaceCourseLecturer(LecturerCourseUpdateRequest request, boolean confirmed);


    void deactivateAssignmentsForCourse(Integer courseId);
}
