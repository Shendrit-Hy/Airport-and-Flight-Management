package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * {@code AnnouncementRepository} ofron metoda për qasje në njoftimet (announcements),
 * me filtrim bazuar në tenantId për mbështetje të arkitekturës multi-tenant.
 */
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    /**
     * Gjen të gjitha njoftimet që i përkasin një tenant-i specifik.
     *
     * @param tenantId identifikuesi i tenant-it
     * @return listë me njoftimet për tenant-in përkatës
     */
    List<Announcement> findByTenantId(String tenantId);
}
