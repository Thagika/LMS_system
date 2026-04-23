package com.lms.lms_backend.Courses.Admin;


import com.lms.lms_backend.Courses.CourseResponse;
import com.lms.lms_backend.Courses.CreateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/courses")
@RequiredArgsConstructor
public class CourseAdminController {
    private final CourseAdminService service;

    @GetMapping
    public ResponseEntity<List<CourseResponse>> getAllDisabledCourses() {
        List<CourseResponse> courses = service.getAllDisabledCourses();
        return ResponseEntity.ok(courses);
    }

    @PostMapping
    public ResponseEntity<CourseResponse> createCourse(@Valid @RequestBody CreateRequest request) {
        CourseResponse created = service.createCourse(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseResponse> updateCourse(
            @PathVariable Integer id,
            @Valid @RequestBody CreateRequest request) {
        CourseResponse updated = service.updateCourse(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(
            @PathVariable Integer id,
            @RequestParam boolean confirmed) {
        service.disableCourse(id, confirmed);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> enableCourse(
            @PathVariable Integer id) {
        service.enableCourse(id);
        return ResponseEntity.noContent().build();
    }
}
