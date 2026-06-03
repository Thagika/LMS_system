package com.lms.lms_backend.assignment;

import com.lms.lms_backend.assignment.dto.LecturerCourseResponse;
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
                .map(this::mapToAssignmentResponse) // Use your new DTO mapping logic(mapToAssignmentResponse)
                .toList();
    }

    @Override
    public List<LecturerCourseResponse> findLectureCourseByKeyword(String keyword) {
        //If the keyword is empty or null, return an empty list immediately (do nothing)
        if (keyword == null || keyword.trim().isEmpty()) {
            return List.of();
        }

        return lecturerCourseRepository.findByCourseCodeContainingIgnoreCase(keyword.trim())
                .stream()
                .map(this::mapToAssignmentResponse) //DTO mapping logic(mapToAssignmentResponse)
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
