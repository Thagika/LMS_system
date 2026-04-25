package com.lms.lms_backend.user.admin;


import com.lms.lms_backend.user.Role;
import com.lms.lms_backend.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/assigner")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> listAllWithRole(
            @RequestParam  Role role
    ) {
        return ResponseEntity.ok(userService.findAllWithRole(role));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @PatchMapping("/{userId}/role")
    public ResponseEntity<Void> assignRole(
            @PathVariable UUID userId,
            @RequestParam Role role) {
        userService.assignRole(userId, role);
        return ResponseEntity.noContent().build();
    }
}
