package org.trishanku.oa.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.trishanku.oa.admin.model.UserDTO;
import org.trishanku.oa.admin.service.BankUserService;
import org.trishanku.oa.admin.service.CustomerUserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/customerusers")
public class CustomerUserController {

    @Autowired
    CustomerUserService customerUserService;

    //Mapping to get all customer users
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllCustomerUsers()
    {
        List<UserDTO> customerUserDTOS = customerUserService.getAllCustomerUsers();
        return new ResponseEntity<>(customerUserDTOS, HttpStatus.OK);
    }

    //Mapping to get all pending customer users
    @GetMapping(path = "/pending")
    public ResponseEntity<List<UserDTO>> getPendingCustomerUsers()
    {
        List<UserDTO> customerUserDTOS = customerUserService.getPendingCustomerUsers();
        return new ResponseEntity<>(customerUserDTOS, HttpStatus.OK);
    }

    //Mapping to get a specific customer user
    @GetMapping(path = "/{userId}")
    public ResponseEntity<UserDTO> getCustomerUserById(@PathVariable(name="userId") String userId)
    {
        UserDTO customerUserDTO = customerUserService.getCustomerUserById(userId);
        return new ResponseEntity<>(customerUserDTO, HttpStatus.OK);
    }

    //Mapping to add a customer user

    @PostMapping()
    public ResponseEntity<UserDTO> addCustomerUser(@Valid @RequestBody UserDTO userDTO)
    {
        UserDTO customerUserDTO = customerUserService.addCustomerUser(userDTO);
        return new ResponseEntity<>(customerUserDTO, HttpStatus.OK);
    }

    //Mapping to modify a customer user

    @PutMapping(path = "/{userId}")
    public ResponseEntity<UserDTO> modifyCustomerUser(@PathVariable(name="userId") String userId, @RequestBody UserDTO userDTO)
    {
        UserDTO customerUserDTO = customerUserService.modifyCustomerUser(userId, userDTO);
        return new ResponseEntity<>(customerUserDTO, HttpStatus.OK);
    }

    //Mapping to delete a customer user
    @PutMapping(path = "/delete/{userId}")
    public ResponseEntity<UserDTO> deleteCustomerUser(@PathVariable(name="userId") String userId)
    {
        UserDTO customerUserDTO = customerUserService.deleteCustomerUser(userId);
        return new ResponseEntity<>(customerUserDTO, HttpStatus.OK);
    }


    //Mapping to authorise a customer user

    @PutMapping(path = "/authorise/{userId}")
    public ResponseEntity<UserDTO> authorizeCustomerUser(@PathVariable(name="userId") String userId)
    {
        UserDTO customerUserDTO = customerUserService.authoriseCustomerUser(userId);
        return new ResponseEntity<>(customerUserDTO, HttpStatus.OK);
    }



}
