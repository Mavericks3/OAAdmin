package org.trishanku.oa.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.trishanku.oa.admin.entity.Agreement;
import org.trishanku.oa.admin.entity.Customer;
import org.trishanku.oa.admin.entity.Product;
import org.trishanku.oa.admin.entity.TransactionStatusEnum;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AgreementRepository extends JpaRepository<Agreement, UUID> {

    Optional<List<Agreement>> findByTransactionStatus(TransactionStatusEnum transactionStatus);

    Optional<Agreement> findByContractDocumentNumber(String contractDocumentNumber);

    Optional<Agreement> findByAnchorCustomerAndBusinessType(Customer anchorCustomerId, Product productCode);

    Optional<Agreement> findByContractReferenceNumber(String contractReferenceNumber);

    Optional<Agreement> findByContractReferenceNumberAndTransactionStatus(String contractReferenceNumber,TransactionStatusEnum transactionStatus);

    @Query(value = "SELECT nextval('admin.\"AgreementSequence\"')", nativeQuery=true)

    String getAgreementSequence();


}
