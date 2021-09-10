package org.trishanku.oa.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<UserDTO>> getAllBankAdmins()
    {
        List<UserDTO> bankAdminDTOS = bankAdminService.getAllBankAdmins();
        return new ResponseEntity<>(bankAdminDTOS, HttpStatus.OK);
    }

    //Mapping to get all pending bank admins
    @GetMapping(path = "/pending")
    public ResponseEntity<List<UserDTO>> getPendingBankAdmins()
    {
        List<UserDTO> bankAdminDTOS = bankAdminService.getPendingBankAdmins();
        return new ResponseEntity<>(bankAdminDTOS, HttpStatus.OK);
    }

    //Mapping to get a specific bank admin
    @GetMapping(path = "/{userId}")
    public ResponseEntity<UserDTO> getBankAdminById(@PathVariable(name="userId") String userId)
    {
        UserDTO bankAdminDTO = bankAdminService.getBankAdminById(userId);
        return new ResponseEntity<>(bankAdminDTO, HttpStatus.OK);
    }

    //Mapping to add a bank admin

    @PostMapping()
    public ResponseEntity<UserDTO> addBankAdmin(@RequestBody UserDTO userDTO)
    {
        UserDTO bankAdminDTO = bankAdminService.addBankAdmin(userDTO);
        return new ResponseEntity<>(bankAdminDTO, HttpStatus.OK);
    }

    //Mapping to modify a bank admin

    @PutMapping(path = "/{userId}")
    public ResponseEntity<UserDTO> modifyBankAdmin(@PathVariable(name="userId") String userId, @RequestBody UserDTO userDTO)
    {
        UserDTO bankAdminDTO = bankAdminService.modifyBankAdmin(userId, userDTO);
        return new ResponseEntity<>(bankAdminDTO, HttpStatus.OK);
    }

    //Mapping to delete a bank admin
    @PutMapping(path = "/delete/{userId}")
    public ResponseEntity<UserDTO> deleteBankAdmin(@PathVariable(name="userId") String userId)
    {
        UserDTO bankAdminDTO = bankAdminService.deleteBankAdmin(userId);
        return new ResponseEntity<>(bankAdminDTO, HttpStatus.OK);
    }


    //Mapping to authorise a bank admin

    @PutMapping(path = "/authorise/{userId}")
    public ResponseEntity<UserDTO> authorizeBankAdmin(@PathVariable(name="userId") String userId)
    {
        UserDTO bankAdminDTO = bankAdminService.authoriseBankAdmin(userId);
        return new ResponseEntity<>(bankAdminDTO, HttpStatus.OK);
    }



}
