package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
}

