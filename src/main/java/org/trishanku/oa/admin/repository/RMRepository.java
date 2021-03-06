package org.trishanku.oa.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.trishanku.oa.admin.entity.RM;
import org.trishanku.oa.admin.entity.TransactionStatusEnum;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RMRepository extends JpaRepository<RM, UUID> {

    Optional<RM> findByRmId(String rmId);
    Optional<RM> findByRmIdAndTransactionStatus(String rmId,TransactionStatusEnum transactionStatus);
    List<RM> findByTransactionStatus(TransactionStatusEnum transactionStatus);

    @Query(value = "SELECT nextval('admin.\"RMSequence\"')", nativeQuery=true)

    String getRMSequence();
}
