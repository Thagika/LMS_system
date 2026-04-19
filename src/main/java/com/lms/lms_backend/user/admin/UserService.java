package com.lms.lms_backend.user.admin;


import com.lms.lms_backend.user.Role;
import com.lms.lms_backend.user.User;
import com.lms.lms_backend.user.UserRepository;
import com.lms.lms_backend.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository repository;

    public @Nullable List<UserResponse> findAllUserRole() {
        return repository.findByRole(Role.USER);
    }

    public void assignRole(UUID userId, Role role) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        user.setRole(role);
        repository.save(user);
    }

}
