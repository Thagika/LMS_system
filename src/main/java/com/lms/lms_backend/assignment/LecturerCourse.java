package com.lms.lms_backend.assignment;

import com.lms.lms_backend.courses.Course;
import com.lms.lms_backend.user.User;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class LecturerCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "lecturer_id") // Points to User table
    private User lecturer;

    @ManyToOne
    @JoinColumn(name = "course_id")  // Points to Course table
    private Course course;

    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean isActive;
}
