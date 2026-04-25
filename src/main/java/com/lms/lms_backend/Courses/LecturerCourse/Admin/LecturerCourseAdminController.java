package com.lms.lms_backend.Courses.LecturerCourse.Admin;


import com.lms.lms_backend.Courses.Admin.AssignRequest;
import com.lms.lms_backend.Courses.LecturerCourse.LecturerCourseResponse;
import com.lms.lms_backend.Courses.LecturerCourse.LecturerCourseUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/lecturer_courses")
@RequiredArgsConstructor
public class LecturerCourseAdminController {

    private final LecturerCourseAdminService service;
    @GetMapping("/active")
    public ResponseEntity<List<LecturerCourseResponse>> getActive() {
        return ResponseEntity.ok(service.getAllActiveAssignedCourseLecturers());
    }

    @GetMapping("/inactive")
    public ResponseEntity<List<LecturerCourseResponse>> getInactive() {
        return ResponseEntity.ok(service.getAllInactiveAssignedCourseLecturers());
    }

    @PostMapping("/assign")
    public ResponseEntity<Void> assignLecturer(@RequestBody AssignRequest request) {
        service.assignExistingLecturerToExistingCourse(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> disableAssignedCourse(
            @PathVariable Integer id,
            @RequestParam boolean confirmed) {
        service.disableAssignedCourse(id, confirmed);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> reassignCourse(
            @PathVariable Integer id) {
        service.reassignCourseToLecturer(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update")
    public ResponseEntity<Void> replaceCourseLecturer(
            @RequestBody LecturerCourseUpdateRequest request,
            @RequestParam boolean confirmed) {
        service.replaceCourseLecturer(request ,confirmed);
        return ResponseEntity.noContent().build();
    }
}
