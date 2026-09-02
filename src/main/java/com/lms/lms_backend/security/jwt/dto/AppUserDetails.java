package com.lms.lms_backend.security.jwt.dto;

import org.springframework.security.core.userdetails.UserDetails;

public interface AppUserDetails extends UserDetails {
    String getRoleName();
}