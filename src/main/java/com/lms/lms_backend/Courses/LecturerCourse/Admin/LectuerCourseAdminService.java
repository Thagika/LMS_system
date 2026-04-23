package com.lms.lms_backend.Courses.LecturerCourse.Admin;

import com.lms.lms_backend.Courses.Admin.AssignRequest;
import com.lms.lms_backend.Courses.CourseService;
import com.lms.lms_backend.Courses.LecturerCourse.LecturerCourseResponse;
import com.lms.lms_backend.Courses.LecturerCourse.LecturerCourseUpdateRequest;

import java.util.List;

public interface LectuerCourseAdminService extends CourseService {
    List<LecturerCourseResponse> getAllActiveAssignedCourseLectuerers();

    List<LecturerCourseResponse> getAllInactiveAssignedCourseLectuerers();

    void assignExistingLectuererExistingCourse(AssignRequest request);

    void disableAssignedCourse(Integer id, boolean confirmed);

    void reassignCourseToLectuerer(Integer id);

    void replaceCourseLectuerer(LecturerCourseUpdateRequest request, boolean confirmed);
}
