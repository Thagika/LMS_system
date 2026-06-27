package com.lms.lms_backend.Enrollment.dto;

public interface EnrollmentProjection {
    Integer getId();
    String getLecturerCourse_Lecturer_FirstName();
    String getLecturerCourse_Lecturer_LastName();
    String getLecturerCourse_Course_Title();
    String getLecturerCourse_Course_Code();
}