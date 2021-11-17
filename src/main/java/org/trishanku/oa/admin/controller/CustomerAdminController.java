package org.trishanku.oa.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.trishanku.oa.admin.model.UserDTO;
import org.trishanku.oa.admin.service.CustomerAdminService;


import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/customeradmins")
public class CustomerAdminController {

    @Autowired
    CustomerAdminService customerAdminService;

    //Mapping to get all customer admins
    @GetMapping
    @PreAuthorize("hasAnyAuthority({'SUPER_ADMIN','BANK_ADMIN_MAKER','BANK_ADMIN_VIEWER','BANK_ADMIN_CHECKER'})")
    public ResponseEntity<List<UserDTO>> getAllCustomerAdmins()
    {
        List<UserDTO> customerAdminDTOS = customerAdminService.getAllCustomerAdmins();
        return new ResponseEntity<>(customerAdminDTOS, HttpStatus.OK);
    }

    //Mapping to get all pending Customer admins
    @GetMapping(path = "/pending")
    @PreAuthorize("hasAnyAuthority({'SUPER_ADMIN','BANK_ADMIN_MAKER','BANK_ADMIN_VIEWER','BANK_ADMIN_CHECKER'})")
    public ResponseEntity<List<UserDTO>> getPendingCustomerAdmins()
    {
        List<UserDTO> customerAdminDTOS = customerAdminService.getPendingCustomerAdmins();
        return new ResponseEntity<>(customerAdminDTOS, HttpStatus.OK);
    }

    //Mapping to get a specific customer admin
    @GetMapping(path = "/{userId}")
    @PreAuthorize("hasAnyAuthority({'SUPER_ADMIN','BANK_ADMIN_MAKER','BANK_ADMIN_VIEWER','BANK_ADMIN_CHECKER'})")
    public ResponseEntity<UserDTO> getCustomerAdminById(@PathVariable(name="userId") String userId)
    {
        UserDTO customerAdminDTO = customerAdminService.getCustomerAdminById(userId);
        return new ResponseEntity<>(customerAdminDTO, HttpStatus.OK);
    }

    //Mapping to add a customer admin

    @PostMapping()
    @PreAuthorize("hasAnyAuthority({'SUPER_ADMIN','BANK_ADMIN_MAKER'})")
    public ResponseEntity<UserDTO> addCustomerAdmin(@RequestBody UserDTO userDTO)
    {
        UserDTO customerAdminDTO = customerAdminService.addCustomerAdmin(userDTO);
        return new ResponseEntity<>(customerAdminDTO, HttpStatus.OK);
    }

    //Mapping to modify a customer admin

    @PutMapping(path = "/{userId}")
    @PreAuthorize("hasAnyAuthority({'SUPER_ADMIN','BANK_ADMIN_MAKER'})")
    public ResponseEntity<UserDTO> modifyCustomerAdmin(@PathVariable(name="userId") String userId, @RequestBody UserDTO userDTO)
    {
        UserDTO customerAdminDTO = customerAdminService.modifyCustomerAdmin(userId, userDTO);
        return new ResponseEntity<>(customerAdminDTO, HttpStatus.OK);
    }

    //Mapping to delete a customer admin
    @PutMapping(path = "/delete/{userId}")
    @PreAuthorize("hasAnyAuthority({'SUPER_ADMIN','BANK_ADMIN_MAKER'})")
    public ResponseEntity<UserDTO> deleteCustomerAdmin(@PathVariable(name="userId") String userId)
    {
        UserDTO customerAdminDTO = customerAdminService.deleteCustomerAdmin(userId);
        return new ResponseEntity<>(customerAdminDTO, HttpStatus.OK);
    }


    //Mapping to authorise a customer admin

    @PutMapping(path = "/authorise/{userId}")
    @PreAuthorize("hasAnyAuthority({'SUPER_ADMIN','BANK_ADMIN_CHECKER'})")
    public ResponseEntity<UserDTO> authorizeCustomerAdmin(@PathVariable(name="userId") String userId)
    {
        UserDTO customerAdminDTO = customerAdminService.authoriseCustomerAdmin(userId);
        return new ResponseEntity<>(customerAdminDTO, HttpStatus.OK);
    }



}
