package org.trishanku.oa.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.trishanku.oa.admin.entity.SBR;
import org.trishanku.oa.admin.entity.TransactionStatusEnum;

import java.util.List;
import java.util.UUID;

@Repository
public interface SBRRepository extends JpaRepository<SBR, UUID> {

    List<SBR> findByTransactionStatus(TransactionStatusEnum transactionStatus);
}
