package com.lms.lms_backend.assignment;

import com.lms.lms_backend.ExceptionHandler.ResourceNotFoundException;
import com.lms.lms_backend.assignment.dto.LecturerCourseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Primary
@Service
@RequiredArgsConstructor
public class LecturerCourseServiceImpl implements LecturerCourseService {

    private final LecturerCourseRepository Repository;
    private final LecturerCourseMapper courseMapper;

    @Override
    public List<LecturerCourseResponse> findLectureCourseByKeyword(String keyword) {
        //If the keyword is empty or null, return an empty list immediately (do nothing)
        if (keyword == null || keyword.trim().isEmpty()) {
            return List.of();
        }

        return Repository.findByCourseCodeContainingIgnoreCase(keyword.trim())
                .stream()
                .map(courseMapper::mapToAssignmentResponse) //DTO mapping logic(mapToAssignmentResponse)
                .toList();
    }

    @Override
    public LecturerCourse getLecturerCourseEntity(Integer id) {
        return Repository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lecturer course not found with id: " + id));
    }

}
