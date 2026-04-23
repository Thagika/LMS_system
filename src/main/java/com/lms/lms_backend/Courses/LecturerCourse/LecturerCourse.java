package com.lms.lms_backend.Courses.LecturerCourse;

import com.lms.lms_backend.Courses.Course;
import com.lms.lms_backend.user.User;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Data
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
