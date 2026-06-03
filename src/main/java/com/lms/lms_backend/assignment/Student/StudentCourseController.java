package com.lms.lms_backend.assignment.Student;

import com.lms.lms_backend.assignment.dto.LecturerCourseResponse;
import com.lms.lms_backend.assignment.LecturerCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/assigned-Courses")
@RequiredArgsConstructor
public class StudentCourseController {

    private final LecturerCourseService service;
    @GetMapping("/filter")
    public ResponseEntity<List<LecturerCourseResponse>> searchLecCourse(@RequestParam("keyword") String keyword) {
        List<LecturerCourseResponse> results = service.findLectureCourseByKeyword(keyword);
        return ResponseEntity.ok(results);
    }
}
