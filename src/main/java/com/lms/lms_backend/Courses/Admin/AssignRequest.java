package com.lms.lms_backend.Courses.Admin;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssignRequest  {
    private Integer course;
    private UUID lecturer;
}
