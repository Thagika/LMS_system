package com.lms.lms_backend.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User , UUID> {
    Optional<User> findByEmailAndIsActiveTrue(String email);
    List<UserDetailsFetcher> findByRoleAndIsActiveTrue(Role role);
    Optional<User> findByIdAndIsActiveTrue(UUID id);
    Optional<User> findByIdAndRoleAndIsActiveTrue(UUID id, Role role);

    boolean existsByEmail(String email);

}
