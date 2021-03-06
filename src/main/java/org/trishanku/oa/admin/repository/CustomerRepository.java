package org.trishanku.oa.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.trishanku.oa.admin.entity.Customer;
import org.trishanku.oa.admin.entity.TransactionStatusEnum;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    Optional<Customer> findByCustomerId(String customerId);
    Optional<Customer> findByCustomerIdAndTransactionStatus(String customerId,TransactionStatusEnum transactionStatus);
    Optional<Customer> findByBank(boolean isBank);
    List<Customer> findByTransactionStatus(TransactionStatusEnum transactionStatus);
    Optional<Customer> findByCustomerIdAndBank(String customerId, boolean isBank);

    @Query(value = "SELECT nextval('admin.\"CustomerSequence\"')", nativeQuery=true)

    String getCustomerSequence();

}
