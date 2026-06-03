package com.lms.lms_backend.user.dto;

import lombok.*;

import java.util.UUID;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private UUID id;
    private String firstname;
    private String lastname;
    private String email;
}
