package com.lms.lms_backend.Courses;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class CreateRequest {
    @NotBlank(message = "Course title is required")
    private String title;

    private String description;

    @NotBlank(message = "Course code is required")
    private String code;


    @Min(value = 1, message = "Credits must be at least 1")
    @Max(3)
    private Integer credits;
}
