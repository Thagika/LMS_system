package com.lms.lms_backend.assignment.dto;
//ensures JPA fetch all data in one go instead of fetching the names and course titles later on in different queries.
public interface LecturerCourseRequest {
    Integer getId();
    String getLecturerFirstName();
    String getLecturerLastName();
    String getCourseTitle();
    String getCourseCode();
}
