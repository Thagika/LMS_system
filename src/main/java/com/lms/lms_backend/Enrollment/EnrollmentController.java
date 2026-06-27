package com.lms.lms_backend.Enrollment;

import com.lms.lms_backend.Enrollment.dto.EnrollmentResponse;
import com.lms.lms_backend.assignment.dto.LecturerCourseUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/enroll")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService service;

//    since the Authfilter uses userEmail we use it to find the user
    @GetMapping
    public ResponseEntity<List<EnrollmentResponse>> listAllEnrollments(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String studentEmail = userDetails.getUsername();
        return ResponseEntity.ok(service.findAllEnrolledCourses(studentEmail));
    }

    @PostMapping("/enrollment")
    public ResponseEntity<Void> enroll(
            @RequestBody LecturerCourseUpdateRequest request,
            @RequestParam String confirmed,
            @AuthenticationPrincipal UserDetails userDetails) {
        service.enrollment(request, confirmed, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/remove_enrollment")
    public ResponseEntity<Void> unenroll(
            @RequestParam boolean confirmed,
            @AuthenticationPrincipal UserDetails userDetails) {
        service.Withdrawal(confirmed, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}
