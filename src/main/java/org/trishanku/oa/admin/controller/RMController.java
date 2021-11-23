package org.trishanku.oa.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.trishanku.oa.admin.model.RMDTO;
import org.trishanku.oa.admin.model.UserDTO;
import org.trishanku.oa.admin.service.RMService;


import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/rms")
public class RMController {

    @Autowired
    RMService rmService;

    //Mapping to get all rm users
    @GetMapping
    @PreAuthorize("hasAnyAuthority({'SUPER_ADMIN','BANK_ADMIN_MAKER','BANK_ADMIN_VIEWER','BANK_ADMIN_CHECKER'})")
    public ResponseEntity<List<RMDTO>> getAllRMUsers()
    {
        List<RMDTO> rmDTOS = rmService.getAllRMUsers();
        return new ResponseEntity<>(rmDTOS, HttpStatus.OK);
    }

    //Mapping to get all pending rm users
    @GetMapping(path = "/pending")
    @PreAuthorize("hasAnyAuthority({'SUPER_ADMIN','BANK_ADMIN_MAKER','BANK_ADMIN_VIEWER','BANK_ADMIN_CHECKER'})")
    public ResponseEntity<List<RMDTO>> getPendingRMUsers()
    {
        List<RMDTO> rmDTOS = rmService.getPendingRMUsers();
        return new ResponseEntity<>(rmDTOS, HttpStatus.OK);
    }

    //Mapping to get a specific rm user
    @GetMapping(path = "/{rmId}")
    @PreAuthorize("hasAnyAuthority({'SUPER_ADMIN','BANK_ADMIN_MAKER','BANK_ADMIN_VIEWER','BANK_ADMIN_CHECKER'})")
    public ResponseEntity<RMDTO> getRMUserById(@PathVariable(name="rmId") String rmId)
    {
        RMDTO rmDTO = rmService.getRMUserById(rmId);
        return new ResponseEntity<>(rmDTO, HttpStatus.OK);
    }

    //Mapping to add a rm user

    @PostMapping()
    @PreAuthorize("hasAnyAuthority({'SUPER_ADMIN','BANK_ADMIN_MAKER'})")
    public ResponseEntity<RMDTO> addRMUser(@RequestBody RMDTO rmDTO)
    {
        RMDTO rmdto = rmService.addRMUser(rmDTO);
        return new ResponseEntity<>(rmdto, HttpStatus.OK);
    }

    //Mapping to modify a rm user

    @PutMapping(path = "/{rmId}")
    @PreAuthorize("hasAnyAuthority({'SUPER_ADMIN','BANK_ADMIN_MAKER'})")
    public ResponseEntity<RMDTO> modifyRMUser(@PathVariable(name="rmId") String rmId, @RequestBody RMDTO rmDTO)
    {
        RMDTO modifiedDTO = rmService.modifyRMUser(rmId, rmDTO);
        return new ResponseEntity<>(modifiedDTO, HttpStatus.OK);
    }

    //Mapping to delete a rm user
    @PutMapping(path = "/delete/{rmId}")
    @PreAuthorize("hasAnyAuthority({'SUPER_ADMIN','BANK_ADMIN_MAKER'})")
    public ResponseEntity<RMDTO> deleteRMUser(@PathVariable(name="rmId") String rmId)
    {
        RMDTO deleteRMUser = rmService.deleteRMUser(rmId);
        return new ResponseEntity<>(deleteRMUser, HttpStatus.OK);
    }


    //Mapping to authorise a rm user

    @PutMapping(path = "/authorise/{rmId}")
    @PreAuthorize("hasAnyAuthority({'SUPER_ADMIN','BANK_ADMIN_CHECKER'})")
    public ResponseEntity<RMDTO> authorizeRMUser(@PathVariable(name="rmId") String rmId)
    {
        RMDTO rmDTO = rmService.authoriseRMUser(rmId);
        return new ResponseEntity<>(rmDTO, HttpStatus.OK);
    }



}
