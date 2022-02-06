package com.hs.coursemanagerback.repository;

import com.hs.coursemanagerback.model.notification.NotificationTerm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationTermRepository extends JpaRepository<NotificationTerm, Long> {
}
