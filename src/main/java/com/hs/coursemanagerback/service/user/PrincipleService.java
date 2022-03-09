package com.hs.coursemanagerback.service.user;

import com.hs.coursemanagerback.model.user.User;
import com.hs.coursemanagerback.model.user.UserDetailsImpl;
import com.hs.coursemanagerback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class PrincipleService {

    private final UserRepository userRepository;

    @Autowired
    public PrincipleService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private long getId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userDetails.getId();
    }

    public User getPrincipalUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userRepository.findById(userDetails.getId()).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public String getPrincipalEmail() {
        User user = userRepository.getById(getId());
        return user.getEmail();
    }

}
