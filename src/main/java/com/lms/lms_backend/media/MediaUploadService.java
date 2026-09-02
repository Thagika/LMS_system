package com.lms.lms_backend.media;

import com.lms.lms_backend.MediaLinker.MediaLinker;
import com.lms.lms_backend.MediaLinker.MediaLinkerRepository;
import com.lms.lms_backend.assignment.LecturerCourse;
import com.lms.lms_backend.assignment.LecturerCourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MediaUploadService {

    private final S3Client s3Client;
    private final MediaRepository mediaRepository;
    private final MediaLinkerRepository mediaLinkerRepository;
    private final LecturerCourseRepository courseRepository;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Transactional
    public MediaLinker uploadMediaToCourse(Integer courseId,
                                           MultipartFile file,
                                           String categoryName,
                                           BigDecimal orderIndex) throws IOException {

        // 1. Validate if the course exists and active  first
        LecturerCourse course = courseRepository.findByIdAndIsActiveTrue(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found with ID: " + courseId));

        // 2. Generate a structured, unique S3 Object Key (path)
//        might be better to have the user ID instead of courseID
        // Group files by course ID and use a UUID prefix to prevent filename collision overwrites
        String originalFileName = file.getOriginalFilename();
        String uniqueFileId = UUID.randomUUID().toString();
        String s3ObjectKey = "courses/" + courseId + "/" + uniqueFileId + "-" + originalFileName;

        // 3. Upload file bytes to private S3 bucket
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(s3ObjectKey)
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        // 4. Create and save the Media metadata entity
        Media media = new Media();
        media.setFileName(originalFileName);
//        only save the key and not the full URL
        media.setS3Url(s3ObjectKey);
        media.setFileSize(file.getSize());
        media.setMimeType(file.getContentType());
        Media savedMedia = mediaRepository.save(media);

        // 5. Create and save the MediaLinker junction record
        MediaLinker linker = new MediaLinker();
        linker.setCourse(course);
        linker.setMedia(savedMedia);
        linker.setCategoryName(categoryName);
        linker.setOrderIndex(orderIndex);

        return mediaLinkerRepository.save(linker);
    }
}
