package org.trishanku.oa.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.trishanku.oa.admin.model.UserDTO;
import org.trishanku.oa.admin.service.BankAdminService;
import org.trishanku.oa.admin.service.SuperAdminService;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/bankadmins")
public class BankAdminController {

    @Autowired
    BankAdminService bankAdminService;

    //Mapping to get all bank admins
    @GetMapping
    @PreAuthorize("hasAnyAuthority({'SUPER_ADMIN','BANK_ADMIN_MAKER','BANK_ADMIN_VIEWER','BANK_ADMIN_CHECKER'})")
    @Transactional
    public ResponseEntity<List<UserDTO>> getAllBankAdmins()
    {
        List<UserDTO> bankAdminDTOS = bankAdminService.getAllBankAdmins();
        return new ResponseEntity<>(bankAdminDTOS, HttpStatus.OK);
    }

    //Mapping to get reference
    @GetMapping(path = "/getNewReference")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN','BANK_ADMIN_MAKER','BANK_ADMIN_VIEWER','BANK_ADMIN_CHECKER')")
    @Transactional
    public ResponseEntity<String> getNewReference()
    {

        String newReference= bankAdminService.getNewReference();
        return new ResponseEntity<>(newReference, HttpStatus.OK);
    }

    //Mapping to get all pending bank admins
    @GetMapping(path = "/pending")
    @PreAuthorize("hasAnyAuthority({'SUPER_ADMIN','BANK_ADMIN_MAKER','BANK_ADMIN_VIEWER','BANK_ADMIN_CHECKER'})")
    @Transactional
    public ResponseEntity<List<UserDTO>> getPendingBankAdmins()
    {
        List<UserDTO> bankAdminDTOS = bankAdminService.getPendingBankAdmins();
        return new ResponseEntity<>(bankAdminDTOS, HttpStatus.OK);
    }

    //Mapping to get a specific bank admin
    @GetMapping(path = "/{userId}")
    @PreAuthorize("hasAnyAuthority({'SUPER_ADMIN','BANK_ADMIN_MAKER','BANK_ADMIN_VIEWER','BANK_ADMIN_CHECKER'})")
    @Transactional
    public ResponseEntity<UserDTO> getBankAdminById(@PathVariable(name="userId") String userId)
    {
        UserDTO bankAdminDTO = bankAdminService.getBankAdminById(userId);
        return new ResponseEntity<>(bankAdminDTO, HttpStatus.OK);
    }

    //Mapping to add a bank admin

    @PostMapping()
    @PreAuthorize("hasAnyAuthority({'SUPER_ADMIN','BANK_ADMIN_MAKER'})")
    @Transactional
    public ResponseEntity<UserDTO> addBankAdmin(@RequestBody UserDTO userDTO)
    {
        UserDTO bankAdminDTO = bankAdminService.addBankAdmin(userDTO);
        return new ResponseEntity<>(bankAdminDTO, HttpStatus.OK);
    }

    //Mapping to modify a bank admin

    @PutMapping(path = "/{userId}")
    @PreAuthorize("hasAnyAuthority({'SUPER_ADMIN','BANK_ADMIN_MAKER'})")
    @Transactional
    public ResponseEntity<UserDTO> modifyBankAdmin(@PathVariable(name="userId") String userId, @RequestBody UserDTO userDTO)
    {
        UserDTO bankAdminDTO = bankAdminService.modifyBankAdmin(userId, userDTO);
        return new ResponseEntity<>(bankAdminDTO, HttpStatus.OK);
    }

    //Mapping to delete a bank admin
    @PutMapping(path = "/delete/{userId}")
    @PreAuthorize("hasAnyAuthority({'SUPER_ADMIN','BANK_ADMIN_MAKER'})")
    @Transactional
    public ResponseEntity<UserDTO> deleteBankAdmin(@PathVariable(name="userId") String userId)
    {
        UserDTO bankAdminDTO = bankAdminService.deleteBankAdmin(userId);
        return new ResponseEntity<>(bankAdminDTO, HttpStatus.OK);
    }


    //Mapping to authorise a bank admin

    @PutMapping(path = "/authorise/{userId}")
    @PreAuthorize("hasAnyAuthority({'SUPER_ADMIN','BANK_ADMIN_CHECKER'})")
    @Transactional
    public ResponseEntity<UserDTO> authorizeBankAdmin(@PathVariable(name="userId") String userId)
    {
        UserDTO bankAdminDTO = bankAdminService.authoriseBankAdmin(userId);
        return new ResponseEntity<>(bankAdminDTO, HttpStatus.OK);
    }



}
