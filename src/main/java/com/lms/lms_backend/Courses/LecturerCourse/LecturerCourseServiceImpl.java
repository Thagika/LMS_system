package com.lms.lms_backend.Courses.LecturerCourse;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Primary
@Service
@RequiredArgsConstructor
public class LecturerCourseServiceImpl implements LecturerCourseService {

    protected final LecturerCourseRepository lecturerCourseRepository;

    @Override
    public List<LecturerCourseResponse> getAllActiveAssignedCourseLecturers() {
        return lecturerCourseRepository.findAllByIsActiveTrue()
                .stream()
                .map(this::mapToAssignmentResponse) // Use your new DTO mapping logic
                .toList();
    }

    protected LecturerCourseResponse mapToAssignmentResponse(LecturerCourse entity) {
        return LecturerCourseResponse.builder()
                .id(entity.getId())
                .lecturerFirstName(entity.getLecturer().getFirstName())
                .lecturerLastName(entity.getLecturer().getLastName())
                .courseTitle(entity.getCourse().getTitle())
                .courseCode(entity.getCourse().getCode())
                .build();
    }
}
