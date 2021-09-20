package org.trishanku.oa.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.trishanku.oa.admin.entity.RM;

import java.util.UUID;

@Repository
public interface RMRepository extends JpaRepository<RM, UUID> {
}
