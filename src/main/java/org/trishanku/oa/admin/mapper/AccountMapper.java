package org.trishanku.oa.admin.mapper;

import org.mapstruct.Mapper;
import org.trishanku.oa.admin.entity.Account;
import org.trishanku.oa.admin.model.AccountDTO;

import java.util.List;

@Mapper
public interface AccountMapper {

    Account accountDTOToAccount(AccountDTO accountDTO);
    AccountDTO accountToAccountDTO(Account account);
    List<Account> accountDTOsToAccounts(List<AccountDTO> accountDTOs);
    List<AccountDTO> accountsToAccountDTOs(List<Account> accounts);

}
