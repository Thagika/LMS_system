package com.lms.lms_backend.assignment;

import com.lms.lms_backend.assignment.dto.LecturerCourseResponse;

import java.util.List;

public interface LecturerCourseService {

//    getter in  LecturerCourseAdminController

    List<LecturerCourseResponse> findLectureCourseByKeyword(String keyword);
}
