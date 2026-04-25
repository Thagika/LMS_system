package com.lms.lms_backend.Courses.LecturerCourse;

import java.util.List;

public interface LecturerCourseService {

//    getter in  LecturerCourseAdminController
    List<LecturerCourseResponse> getAllActiveAssignedCourseLecturers();
}
