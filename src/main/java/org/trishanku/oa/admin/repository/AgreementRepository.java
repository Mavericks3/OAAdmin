package org.trishanku.oa.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.trishanku.oa.admin.entity.Agreement;
import org.trishanku.oa.admin.entity.TransactionStatusEnum;

import java.util.List;
import java.util.UUID;

@Repository
public interface AgreementRepository extends JpaRepository<Agreement, UUID> {

    List<Agreement> findByTransactionStatus(TransactionStatusEnum transactionStatus);

    Agreement findByContractDocumentNumber(String contractDocumentNumber);

    Agreement findByContractReferenceNumber(String contractReferenceNumber);



}
