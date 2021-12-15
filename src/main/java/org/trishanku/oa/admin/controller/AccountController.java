package org.trishanku.oa.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.trishanku.oa.admin.entity.Product;
import org.trishanku.oa.admin.mapper.AccountMapper;
import org.trishanku.oa.admin.mapper.ProductMapper;
import org.trishanku.oa.admin.model.AccountDTO;
import org.trishanku.oa.admin.model.ProductDTO;
import org.trishanku.oa.admin.model.RMDTO;
import org.trishanku.oa.admin.repository.AccountRepository;
import org.trishanku.oa.admin.repository.ProductRepository;
import org.trishanku.oa.admin.service.AccountService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    @Autowired
    AccountService accountService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority({'BANK_USER_MAKER' })")
    @Transactional
    public ResponseEntity<AccountDTO> addAccount(@RequestBody AccountDTO accountDTO)
    {
        AccountDTO addAccount = accountService.addAccount(accountDTO);
        return new ResponseEntity<>(addAccount, HttpStatus.OK);
    }


    @GetMapping
    @PreAuthorize("hasAnyAuthority({'BANK_USER_MAKER','BANK_USER_VIEWER','BANK_USER_CHECKER'})")
    @Transactional
    public ResponseEntity<List<AccountDTO>> getAllAccounts(@RequestParam(name = "transactionStatus",required=false)  String transactionStatus,
                                                           @RequestParam(name ="status",required=false) String status,
                                                           @RequestParam(name ="accountId",required=false) String accountId,
                                                           @RequestParam(name ="accountName",required=false) String accountName,
                                                           @RequestParam(name ="accountType",required=false) String accountType,
                                                           @RequestParam(name ="accountCurrency",required=false) String accountCurrency)
    {
        List<AccountDTO> accountDTOs = accountService.getAccounts(accountId,accountName,accountType,accountCurrency,status,transactionStatus);
        return new ResponseEntity<>(accountDTOs, HttpStatus.OK);
    }

    @PutMapping()
    @PreAuthorize("hasAnyAuthority({'BANK_USER_MAKER'})")
    @Transactional
    public ResponseEntity<AccountDTO> modifyAccount(@RequestBody AccountDTO accountDTO)
    {
        AccountDTO modifiedDTO = accountService.modifyAccount(accountDTO);
        return new ResponseEntity<>(modifiedDTO, HttpStatus.OK);
    }

    @PutMapping(path = "/delete")
    @PreAuthorize("hasAnyAuthority({'BANK_USER_MAKER'})")
    @Transactional
    public ResponseEntity<AccountDTO> deleteAccount(@RequestBody AccountDTO accountDTO)
    {
        AccountDTO deletedDTO = accountService.deleteAccount(accountDTO);
        return new ResponseEntity<>(deletedDTO, HttpStatus.OK);
    }

    @PutMapping(path = "/authorise")
    @PreAuthorize("hasAnyAuthority({'BANK_USER_CHECKER'})")
    @Transactional
    public ResponseEntity<AccountDTO> authoriseAccount(@RequestBody AccountDTO accountDTO)
    {
        AccountDTO authorisedDTO = accountService.authoriseAccount(accountDTO);
        return new ResponseEntity<>(authorisedDTO, HttpStatus.OK);
    }

}
