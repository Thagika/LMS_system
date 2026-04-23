package com.lms.lms_backend.Courses.LecturerCourse.Admin;


import com.lms.lms_backend.Courses.Admin.AssignRequest;
import com.lms.lms_backend.Courses.LecturerCourse.LecturerCourseResponse;
import com.lms.lms_backend.Courses.LecturerCourse.LecturerCourseUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/lectuerer_courses")
@RequiredArgsConstructor
public class LectuererCourseAdminController{

    private final LectuerCourseAdminService service;
    @GetMapping("/active")
    public ResponseEntity<List<LecturerCourseResponse>> getActive() {
        return ResponseEntity.ok(service.getAllActiveAssignedCourseLectuerers());
    }

    @GetMapping("/inactive")
    public ResponseEntity<List<LecturerCourseResponse>> getInactive() {
        return ResponseEntity.ok(service.getAllInactiveAssignedCourseLectuerers());
    }

    @PostMapping("/assign")
    public ResponseEntity<Void> assignLecturer(@RequestBody AssignRequest request) {
        service.assignExistingLectuererExistingCourse(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> disableassignedCourse(
            @PathVariable Integer id,
            @RequestParam boolean confirmed) {
        service.disableAssignedCourse(id, confirmed);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> reassignCourse(
            @PathVariable Integer id) {
        service.reassignCourseToLectuerer(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Void> replaceCourseLectuerer(
            @RequestBody LecturerCourseUpdateRequest request,
            @RequestParam boolean confirmed) {
        service.replaceCourseLectuerer(request ,confirmed);
        return ResponseEntity.noContent().build();
    }
}
