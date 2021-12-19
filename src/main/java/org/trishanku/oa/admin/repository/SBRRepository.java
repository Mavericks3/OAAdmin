package org.trishanku.oa.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.trishanku.oa.admin.entity.Agreement;
import org.trishanku.oa.admin.entity.Customer;
import org.trishanku.oa.admin.entity.SBR;
import org.trishanku.oa.admin.entity.TransactionStatusEnum;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SBRRepository extends JpaRepository<SBR, UUID> {

    Optional<List<SBR>> findByTransactionStatus(TransactionStatusEnum transactionStatus);
    Optional<List<SBR>> findByAgreementAndTransactionStatus(Agreement agreement, TransactionStatusEnum transactionStatus);

    Optional<SBR> findBySbrId(String sbrId);

    Optional<List<SBR>> findByAnchorCustomer(Customer anchorCustomer);

    Optional<List<SBR>> findByCounterParty(Customer counterParty);

    Optional<SBR> findByAgreementAndAnchorCustomerAndCounterParty(Agreement agreement, Customer anchorCustomer, Customer counterParty);

    Optional<List<SBR>> findByAgreement(Agreement agreement);

    @Query(value = "SELECT nextval('admin.\"SBRSequence\"')", nativeQuery=true)

    String getSBRSequence();
}
