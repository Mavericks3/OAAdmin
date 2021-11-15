package org.trishanku.oa.admin.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.trishanku.oa.admin.entity.Audit;

import java.util.UUID;

public interface AuditRepository extends JpaRepository<Audit, UUID> {
}
