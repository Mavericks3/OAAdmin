package org.trishanku.oa.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.trishanku.oa.admin.model.UserDTO;
import org.trishanku.oa.admin.service.BankAdminService;
import org.trishanku.oa.admin.service.BankUserService;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/bankusers")
public class BankUserController {

    @Autowired
    BankUserService bankUserService;

    //Mapping to get all bank users
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllBankUsers()
    {
        List<UserDTO> bankUserDTOS = bankUserService.getAllBankUsers();
        return new ResponseEntity<>(bankUserDTOS, HttpStatus.OK);
    }

    //Mapping to get all pending bank users
    @GetMapping(path = "/pending")
    public ResponseEntity<List<UserDTO>> getPendingBankUsers()
    {
        List<UserDTO> bankUserDTOS = bankUserService.getPendingBankUsers();
        return new ResponseEntity<>(bankUserDTOS, HttpStatus.OK);
    }

    //Mapping to get a specific bank user
    @GetMapping(path = "/{userId}")
    public ResponseEntity<UserDTO> getBankUserById(@PathVariable(name="userId") String userId)
    {
        UserDTO bankUserDTO = bankUserService.getBankUserById(userId);
        return new ResponseEntity<>(bankUserDTO, HttpStatus.OK);
    }

    //Mapping to add a bank user

    @PostMapping()
    public ResponseEntity<UserDTO> addBankUser(@RequestBody UserDTO userDTO)
    {
        UserDTO bankUserDTO = bankUserService.addBankUser(userDTO);
        return new ResponseEntity<>(bankUserDTO, HttpStatus.OK);
    }

    //Mapping to modify a bank user

    @PutMapping(path = "/{userId}")
    public ResponseEntity<UserDTO> modifyBankUser(@PathVariable(name="userId") String userId, @RequestBody UserDTO userDTO)
    {
        UserDTO bankUserDTO = bankUserService.modifyBankUser(userId, userDTO);
        return new ResponseEntity<>(bankUserDTO, HttpStatus.OK);
    }

    //Mapping to delete a bank user
    @PutMapping(path = "/delete/{userId}")
    public ResponseEntity<UserDTO> deleteBankUser(@PathVariable(name="userId") String userId)
    {
        UserDTO bankUserDTO = bankUserService.deleteBankUser(userId);
        return new ResponseEntity<>(bankUserDTO, HttpStatus.OK);
    }


    //Mapping to authorise a bank user

    @PutMapping(path = "/authorise/{userId}")
    public ResponseEntity<UserDTO> authorizeBankUser(@PathVariable(name="userId") String userId)
    {
        UserDTO bankUserDTO = bankUserService.authoriseBankUser(userId);
        return new ResponseEntity<>(bankUserDTO, HttpStatus.OK);
    }



}
