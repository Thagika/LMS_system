package com.lms.lms_backend.user;


import com.lms.lms_backend.user.dto.UserDetailsFetcher;
import com.lms.lms_backend.user.dto.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponse mapToAssignmentResponse(UserDetailsFetcher projection) {
        return UserResponse.builder()
                .id(projection.getId())
                .firstname(projection.getFirstName())
                .lastname(projection.getLastName())
                .email(projection.getEmail())
                .build();
    }
}
