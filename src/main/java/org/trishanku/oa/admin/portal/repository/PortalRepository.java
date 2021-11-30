package org.trishanku.oa.admin.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.trishanku.oa.admin.entity.TransmissionStatusEnum;
import org.trishanku.oa.admin.portal.entity.Portal;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PortalRepository extends JpaRepository<Portal, UUID> {

    Optional<List<Portal>> findByStatus(TransmissionStatusEnum transmissionStatus);
}
