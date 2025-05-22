package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for accessing announcements with tenant-based filtering.
 */
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    /**
     * Finds all announcements belonging to the given tenant.
     *
     * @param tenantId the tenant identifier
     * @return list of announcements for the tenant
     */
    List<Announcement> findByTenantId(String tenantId);
}
