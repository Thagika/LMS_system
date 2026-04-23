package com.lms.lms_backend.Courses.Admin;

import com.lms.lms_backend.Courses.CourseResponse;
import com.lms.lms_backend.Courses.CourseService;
import com.lms.lms_backend.Courses.CreateRequest;

import java.util.List;

public interface CourseAdminService extends CourseService {
        /**
     * Creates a new course record.
     * @param request Data transfer object containing course details.
     * @return The created course details.
     */
    CourseResponse createCourse(CreateRequest request);

    /**
     * Updates an existing course.
     * @param id The UUID of the course to update.
     * @param request The updated course data.
     * @return The updated course details.
     */
    CourseResponse updateCourse(Integer id, CreateRequest request);

    /**
     * Deletes a course after verifying authorization and existence.
     * Implementation should handle the "confirmation" logic via state checks
     * or by requiring a specific flag.
     * @param id The UUID of the course to remove.
     * @param confirmed A flag to ensure the deletion is intentional.
     */
    void disableCourse(Integer id, boolean confirmed);
    
    void enableCourse(Integer id);

    List<CourseResponse> getAllDisabledCourses();
}
