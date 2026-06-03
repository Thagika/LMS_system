package com.lms.lms_backend.courses;


import com.lms.lms_backend.courses.dto.CourseResponse;

import java.util.List;

public interface CourseService {

    List<CourseResponse> getAllCourses();

    CourseResponse getCourseById(Integer id);
}
