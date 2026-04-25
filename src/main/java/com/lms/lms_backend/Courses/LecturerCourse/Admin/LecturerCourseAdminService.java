package com.lms.lms_backend.Courses.LecturerCourse.Admin;

import com.lms.lms_backend.Courses.Admin.AssignRequest;
import com.lms.lms_backend.Courses.LecturerCourse.LecturerCourseResponse;
import com.lms.lms_backend.Courses.LecturerCourse.LecturerCourseService;
import com.lms.lms_backend.Courses.LecturerCourse.LecturerCourseUpdateRequest;

import java.util.List;

public interface LecturerCourseAdminService extends LecturerCourseService {

    List<LecturerCourseResponse> getAllInactiveAssignedCourseLecturers();

    void assignExistingLecturerToExistingCourse(AssignRequest request);

    void disableAssignedCourse(Integer id, boolean confirmed);

    void reassignCourseToLecturer(Integer id);

    void replaceCourseLecturer(LecturerCourseUpdateRequest request, boolean confirmed);
}
