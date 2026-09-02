package com.lms.lms_backend.media;


import com.lms.lms_backend.MediaLinker.MediaLinker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/V1/media")
public class MediaUploadController {

    private final MediaUploadService mediaUploadService;

    @PostMapping("/{courseId}/media/upload")
    public ResponseEntity<MediaLinker> uploadMedia(
            @PathVariable Integer courseId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("categoryName") String categoryName,
            @RequestParam("orderIndex") BigDecimal orderIndex) {

        try {
            MediaLinker result = mediaUploadService.uploadMediaToCourse(courseId, file, categoryName, orderIndex);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
