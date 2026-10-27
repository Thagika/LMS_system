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

import java.io.IOException;
import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/media")
public class MediaUploadController {

    private final MediaUploadService mediaUploadService;

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
            System.out.println(">>> [Upload Error] IOException occurred: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            System.out.println(">>> [Upload Error] General or AWS Exception occurred!");
            e.printStackTrace(); // This prints the entire stack trace to your terminal logs
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}