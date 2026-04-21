package com.lms.lms_backend.user;

import java.util.UUID;

public interface UserDetailsFetcher {
    UUID getId();
    String getFirstName();
    String getLastName();
    String getEmail();
}
