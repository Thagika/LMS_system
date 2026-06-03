package com.lms.lms_backend.security.jwt.auth;

import com.lms.lms_backend.security.jwt.auth.dto.AuthenticationResponse;
import com.lms.lms_backend.security.jwt.auth.dto.RegisterRequest;
import org.jspecify.annotations.Nullable;

public interface AuthenticationService {
    @Nullable
    AuthenticationResponse register(RegisterRequest request);

    @Nullable AuthenticationResponse authenticate(AuthenticationRequest request);
}
