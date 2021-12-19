package org.trishanku.oa.admin.service;

import org.trishanku.oa.admin.model.AccountDTO;

import java.util.List;

public interface AccountService {

    AccountDTO addAccount(AccountDTO accountDTO);
    AccountDTO modifyAccount(AccountDTO accountDTO);
    AccountDTO deleteAccount(AccountDTO accountDTO);
    AccountDTO authoriseAccount(AccountDTO accountDTO);
    List<AccountDTO> getAccounts(String accountId, String accountName, String accountType, String accountCurrency, String status, String transactionStatus);
    String getNewReference();

}
