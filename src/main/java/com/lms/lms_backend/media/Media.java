package com.lms.lms_backend.media;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "media")
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String fileName;
    private String s3Url; //path to the media file
    private Long fileSize;
    private String mimeType;
}