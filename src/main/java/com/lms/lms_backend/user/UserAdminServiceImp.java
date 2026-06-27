package com.lms.lms_backend.user;


import com.lms.lms_backend.ExceptionHandler.ResourceNotFoundException;
import com.lms.lms_backend.user.dto.UserDetailsFetcher;
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
    private final UserMapper userMapper;

    public @Nullable List<UserResponse> findAllWithRole(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }
            return repository.findByRoleAndIsActiveTrue(role)
                    .stream()
                    .map(userMapper::mapToAssignmentResponse)
                    .toList();
    }

    public void assignRole(UUID userId, Role role) {
        User user = repository.findByIdAndIsActiveTrue(userId , User.class)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        user.setRole(role);
        repository.save(user);
    }

    @Override
    public User getActiveUserEntity(UUID id) {
        return repository.findByIdAndIsActiveTrue(id, User.class)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }
    @Override
    public User getActiveLecturerEntity(UUID id) {
        return repository.findByIdAndRoleAndIsActiveTrue(id, Role.LECTURER)
                .orElseThrow(() -> new ResourceNotFoundException("User not found or is not a LECTURER with id: " + id));
    }

    @Override
    public User getUserByEmail(String email) {
        return repository.findByEmailAndIsActiveTrue(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }

    public UserResponse findUserById(UUID id) {
        UserDetailsFetcher user = repository.findByIdAndIsActiveTrue(id, UserDetailsFetcher.class)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return  userMapper.mapToAssignmentResponse(user);
    }
}
