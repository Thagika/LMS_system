package com.lms.lms_backend.user;


import com.lms.lms_backend.user.dto.UserRequest;
import com.lms.lms_backend.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/assigner")
@RequiredArgsConstructor
public class UserAdminController {

    private final UserAdminService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> listAllWithRole(
            @RequestParam  Role role
    ) {
        return ResponseEntity.ok(userService.findAllWithRole(role));
    }

    @PostMapping
    public ResponseEntity<UserResponse> getUserByEmail(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userService.findUserByEmail(userRequest));
    }


    @PatchMapping("/{userId}/role")
    public ResponseEntity<Void> assignRole(
            @PathVariable UUID userId,
            @RequestParam Role role) {
        userService.assignRole(userId, role);
        return ResponseEntity.noContent().build();
    }
}
