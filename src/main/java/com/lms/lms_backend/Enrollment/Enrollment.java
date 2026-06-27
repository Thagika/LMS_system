package com.lms.lms_backend.Enrollment;

import com.lms.lms_backend.assignment.LecturerCourse;
import com.lms.lms_backend.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicUpdate
@Table(name = "enrollments")

public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "lec_course") // Points to LecturerCourse table
    private LecturerCourse lecturer_course;

    @ManyToOne
    @JoinColumn(name = "student_id") // Points to User table
    private User student;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdDate;

    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean isActive;

//    the value contains the grades and the number of attempts made attempts are in
//    the grades are the first 8 bits and the attempts in the remaining half
    @Column(name = "grade_data", columnDefinition = "SMALLINT")
    private Integer gradeData;
}
