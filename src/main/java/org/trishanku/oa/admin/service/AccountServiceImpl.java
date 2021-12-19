package org.trishanku.oa.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.trishanku.oa.admin.entity.Account;
import org.trishanku.oa.admin.entity.CurrencyEnum;
import org.trishanku.oa.admin.entity.TransactionStatusEnum;
import org.trishanku.oa.admin.jwtauthentication.configuration.service.JWTUtil;
import org.trishanku.oa.admin.mapper.AccountMapper;
import org.trishanku.oa.admin.model.AccountDTO;
import org.trishanku.oa.admin.repository.AccountRepository;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    JWTUtil jwtUtil;

    @Override
    public AccountDTO addAccount(AccountDTO accountDTO) {

        if(accountRepository.findByAccountId(accountDTO.getAccountId()).isPresent()) throw new RuntimeException("Account with id " + accountDTO.getAccountId() + " already exists");
        Account account = accountMapper.accountDTOToAccount(accountDTO);
        account.setSystemId(UUID.randomUUID());
        account.setCreationDetails(jwtUtil.extractUsernameFromRequest());
        Account accountSaved = accountRepository.save(account);
        return accountMapper.accountToAccountDTO(accountSaved);
    }

    @Override
    public List<AccountDTO> getAccounts(String accountId, String accountName, String accountType, String accountCurrency, String status, String transactionStatus) {
        Optional<List<Account>> optionalAccounts = accountRepository.findByAccountIdAndNameAndTypeAndCurrencyAndStatusAndTransactionStatus(accountId,
                        accountName,accountType, (CurrencyEnum.getValue(accountCurrency)==null)?null:CurrencyEnum.getValue(accountCurrency),
                (status==null)?true:Boolean.parseBoolean(status), TransactionStatusEnum.getValue(transactionStatus));
        if(optionalAccounts.isEmpty())
            return null;
        else
            return accountMapper.accountsToAccountDTOs(optionalAccounts.get());

    }

    @Override
    public AccountDTO modifyAccount(AccountDTO accountDTO) {
        Optional<Account> account =accountRepository.findByAccountId(accountDTO.getAccountId());
        if(account.isEmpty()) throw new RuntimeException("Account with id " + accountDTO.getAccountId() + " does not exist");
        Account accountDetails = accountMapper.accountDTOToAccount(accountDTO);
        accountDetails.setSystemId(account.get().getSystemId());
        accountDetails.setCreatedUser(account.get().getCreatedUser());
        accountDetails.setCreatedDate(account.get().getCreatedDate());
        accountDetails.setModificationDetails(jwtUtil.extractUsernameFromRequest());
        Account accountSaved = accountRepository.save(accountDetails);
        return accountMapper.accountToAccountDTO(accountSaved);

    }

    @Override
    public AccountDTO deleteAccount(AccountDTO accountDTO) {
        Optional<Account> account =accountRepository.findByAccountId(accountDTO.getAccountId());
        if(account.isEmpty()) throw new RuntimeException("Account with id " + accountDTO.getAccountId() + " does not exist");
        account.get().setDeleteFlag(true);
        account.get().setModificationDetails(jwtUtil.extractUsernameFromRequest());
        Account accountSaved = accountRepository.save(account.get());
        return accountMapper.accountToAccountDTO(accountSaved);
    }

    @Override
    public AccountDTO authoriseAccount(AccountDTO accountDTO) {
        Optional<Account> account =accountRepository.findByAccountId(accountDTO.getAccountId());
        if(account.isEmpty()) throw new RuntimeException("Account with id " + accountDTO.getAccountId() + " does not exist");
        if(account.get().isDeleteFlag()==true) {
            accountRepository.delete(account.get());
            return null;
        }
        account.get().setAuthorizationDetails(jwtUtil.extractUsernameFromRequest());
        Account accountSaved = accountRepository.save(account.get());
        return accountMapper.accountToAccountDTO(accountSaved);
    }

    @Override
    public String getNewReference() {

        String accountSequence = accountRepository.getAccountSequence();
        return "Account" + Calendar.getInstance().get(Calendar.YEAR) + accountSequence;
    }
}
