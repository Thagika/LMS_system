package com.lms.lms_backend.Courses;


import java.util.List;

public interface CourseService {

    List<CourseResponse> getAllCourses();

    CourseResponse getCourseById(Integer id);
}
