package org.trishanku.oa.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.trishanku.oa.admin.entity.RM;
import org.trishanku.oa.admin.entity.TransactionStatusEnum;

import java.util.List;
import java.util.UUID;

@Repository
public interface RMRepository extends JpaRepository<RM, UUID> {

    RM findByRmId(String rmId);
    List<RM> findByTransactionStatus(TransactionStatusEnum transactionStatus);
}
