package com.lms.lms_backend.media;

import com.lms.lms_backend.MediaLinker.MediaLinker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/media")
public class MediaUploadController {

    private final MediaUploadService mediaUploadService;
    private static final Logger logger = LoggerFactory.getLogger(MediaUploadController.class);

    @PostMapping("/{courseId}/upload")
    public ResponseEntity<MediaLinker> uploadMedia(
            @PathVariable Integer courseId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("categoryName") String categoryName,
            @RequestParam("orderIndex") BigDecimal orderIndex) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (auth != null && auth.getPrincipal() instanceof UserDetails ud) ? ud : null;

        try {
            MediaLinker result = mediaUploadService.uploadMediaToCourse(courseId, file, categoryName, orderIndex, userDetails);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (IOException e) {
            logger.error("[Upload Error] Input/Output exception occurred during file processing!", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.error("[Upload Error] General or AWS Exception occurred!", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}