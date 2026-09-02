package com.lms.lms_backend.MediaLinker;


import com.lms.lms_backend.assignment.LecturerCourse;
import com.lms.lms_backend.media.Media;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "course_media")
public class MediaLinker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    this column is expected to show the decimal numebrs so it will show to
//    which catagory and the order the video file is expect in the list
    @Column(name = "order", nullable = false, columnDefinition = "NUMERIC", precision = 10, scale = 2)
    private BigDecimal orderIndex; // Handles the sequence order on each category(1, 2, 3...)

    private String CategoryName; //since 1 topic can have mutli vids this makes sure all will be grouped

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private LecturerCourse course; // Your existing course entity

    @ManyToOne
    @JoinColumn(name = "media_id", nullable = false)
    private Media media; // Your new media entity
}

