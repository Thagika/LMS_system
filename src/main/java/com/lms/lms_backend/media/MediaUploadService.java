package com.lms.lms_backend.media;

import com.lms.lms_backend.MediaLinker.MediaLinker;
import com.lms.lms_backend.MediaLinker.MediaLinkerRepository;
import com.lms.lms_backend.assignment.LecturerCourse;
import com.lms.lms_backend.assignment.LecturerCourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
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
                                           BigDecimal orderIndex,
                                           UserDetails userDetails) throws IOException {

        // 1. Validate file is not empty and has a filename
        if (file == null || file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File must not be empty");
        }
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null || originalFileName.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File must have a valid filename");
        }

        // 2. Validate category and orderIndex
        if (categoryName == null || categoryName.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category name is required");
        }
        if (orderIndex == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order index is required");
        }

        // 3. Validate if the course assignment exists and is active
        LecturerCourse course = courseRepository.findByIdAndIsActiveTrue(courseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found with ID: " + courseId));

        // 4. Validate uploader authentication, role, and course assignment ownership
        if (userDetails == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User must be authenticated");
        }

        userDetails.getAuthorities();
        boolean isAdmin = userDetails.getAuthorities().stream()
                        .anyMatch(a -> "ADMIN".equalsIgnoreCase(a.getAuthority()));

        userDetails.getAuthorities();
        boolean isLecturer = userDetails.getAuthorities().stream()
                        .anyMatch(a -> "LECTURER".equalsIgnoreCase(a.getAuthority()));

        if (!isLecturer && !isAdmin) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only lecturers and admins are allowed to upload media");
        }

        if (!isAdmin) {
            if (course.getLecturer() == null || !course.getLecturer().getEmail().equalsIgnoreCase(userDetails.getUsername())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to upload media to this course");
            }
        }

        // 5. Validate file name uniqueness for this course
        if (mediaLinkerRepository.existsByCourseIdAndMedia_FileName(courseId, originalFileName)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A file with the name '" + originalFileName + "' already exists in this course");
        }

//         6. Validate order index uniqueness for this course category
        if (mediaLinkerRepository.existsByCourseIdAndCategoryNameAndOrderIndex(courseId, categoryName, orderIndex)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Order index " + orderIndex + " already exists in category '" + categoryName + "' for this course");
        }

        // 7. Generate a structured, unique S3 Object Key (path)
        String uniqueFileId = UUID.randomUUID().toString();
        String s3ObjectKey = "courses/" + courseId + "/" + uniqueFileId + "-" + originalFileName;

        // 8. Upload file bytes to private S3 bucket
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(s3ObjectKey)
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        // 9. Create and save the Media metadata entity
        Media media = new Media();
        media.setFileName(originalFileName);
        media.setS3Url(s3ObjectKey);
        media.setFileSize(file.getSize());
        media.setMimeType(file.getContentType());
        Media savedMedia = mediaRepository.save(media);

        // 10. Create and save the MediaLinker junction record
        MediaLinker linker = new MediaLinker();
        linker.setCourse(course);
        linker.setMedia(savedMedia);
        linker.setCategoryName(categoryName);
        linker.setOrderIndex(orderIndex);

        return mediaLinkerRepository.save(linker);
    }
}
