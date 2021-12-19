package org.trishanku.oa.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.trishanku.oa.admin.entity.Account;
import org.trishanku.oa.admin.entity.CurrencyEnum;
import org.trishanku.oa.admin.entity.TransactionStatusEnum;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

//    @Query("select a from Account a where"+
//            ":accountId is null or a.accountId = :accountId "
//    )
    Optional<Account> findByAccountId(String accountId);



    @Query("select a from Account a where"+
            "(:accountId is null or a.accountId = :accountId) and "+
            "(:accountName is null or a.name = :accountName) and "+
            "(:accountType is null or a.type = :accountType) and "+
            "(:accountCurrency is null or a.currency = :accountCurrency) and "+
            "(:status is null or a.status = :status) and "+
            "(:transactionStatus is null or a.transactionStatus = :transactionStatus)"
    )

Optional<List<Account>> findByAccountIdAndNameAndTypeAndCurrencyAndStatusAndTransactionStatus(String accountId,
                                                                                                  String accountName,
                                                                                                  String accountType,
                                                                                                  CurrencyEnum accountCurrency,
                                                                                                  boolean status,
                                                                                                  TransactionStatusEnum transactionStatus);

    @Query("select a from Account a where"+
            "(:accountId is null or a.accountId = :accountId) and "+
            "(:accountName is null or a.name = :accountName) and "+
            "(:accountType is null or a.type = :accountType) and"+
            "(:accountCurrency is null or a.currency = :accountCurrency) "

    )
    Optional<List<Account>>findByAccountIdAndNameAndTypeAndCurrency(String accountId,
                                                         String accountName,
                                                         String accountType,
                                                         CurrencyEnum accountCurrency);

    @Query(value = "SELECT nextval('admin.\"AccountSequence\"')", nativeQuery=true)

    String getAccountSequence();


}
