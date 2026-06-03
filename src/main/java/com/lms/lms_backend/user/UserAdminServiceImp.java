package com.lms.lms_backend.user;


import com.lms.lms_backend.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserAdminServiceImp implements UserAdminService {

    private final UserRepository repository;

    public @Nullable List<UserResponse> findAllWithRole(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }
            return repository.findByRoleAndIsActiveTrue(role)
                    .stream()
                    .map(user -> UserResponse.builder()
                            .id(user.getId())
                            .firstname(user.getFirstName())
                            .lastname(user.getLastName())
                            .email(user.getEmail())
                            .build())
                    .toList();
    }

    public void assignRole(UUID userId, Role role) {
        User user = repository.findByIdAndIsActiveTrue(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        user.setRole(role);
        repository.save(user);
    }

    public UserResponse findUserById(UUID id) {
        User user = repository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        return UserResponse.builder()
                .id(user.getId())
                .firstname(user.getFirstName())
                .lastname(user.getLastName())
                .email(user.getEmail())
                .build();
    }
}
