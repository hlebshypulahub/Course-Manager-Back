package com.hs.coursemanagerback.repository;

import com.hs.coursemanagerback.model.user.Role;
import com.hs.coursemanagerback.model.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(UserRole name);
}