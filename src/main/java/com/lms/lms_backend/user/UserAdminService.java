package com.lms.lms_backend.user;

import com.lms.lms_backend.user.dto.UserResponse;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public interface UserAdminService {
    @Nullable List<UserResponse> findAllWithRole(Role role);

    @Nullable UserResponse findUserById(UUID id);

    void assignRole(UUID userId, Role role);
}
