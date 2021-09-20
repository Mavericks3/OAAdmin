package org.trishanku.oa.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.trishanku.oa.admin.exception.ResourceAlreadyExistsException;
import org.trishanku.oa.admin.model.UserDTO;
import org.trishanku.oa.admin.service.SuperAdminService;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/superadmins")
public class SuperAdminController {

    @Autowired
    SuperAdminService superAdminService;

    //Mapping to get all super admins
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllSuperAdmins()
    {
        List<UserDTO> superAdminDTOS = superAdminService.getAllSuperAdmins();
        return new ResponseEntity<>(superAdminDTOS, HttpStatus.OK);
    }

    //Mapping to get all pending super admins
    @GetMapping(path = "/pending")
    public ResponseEntity<List<UserDTO>> getPendingSuperAdmins()
    {
        List<UserDTO> superAdminDTOS = superAdminService.getPendingSuperAdmins();
        return new ResponseEntity<>(superAdminDTOS, HttpStatus.OK);
    }

    //Mapping to get a specific super admin
    @GetMapping(path = "/{userId}")
    public ResponseEntity<UserDTO> getSuperAdminById(@PathVariable(name="userId") String userId)
    {
        UserDTO superAdminDTO = superAdminService.getSuperAdminById(userId);
        return new ResponseEntity<>(superAdminDTO, HttpStatus.OK);
    }

    //Mapping to add a super admin

    @PostMapping()
    public ResponseEntity<UserDTO> addSuperAdmin(@RequestBody UserDTO userDTO) throws ResourceAlreadyExistsException {
        UserDTO superAdminDTO = superAdminService.addSuperAdmin(userDTO);
        return new ResponseEntity<>(superAdminDTO, HttpStatus.OK);
    }

    //Mapping to modify a super admin

    @PutMapping(path = "/{userId}")
    public ResponseEntity<UserDTO> modifySuperAdmin(@PathVariable(name="userId") String userId, @RequestBody UserDTO userDTO)
    {
        UserDTO superAdminDTO = superAdminService.modifySuperAdmin(userId, userDTO);
        return new ResponseEntity<>(superAdminDTO, HttpStatus.OK);
    }

    //Mapping to delete a super admin
    @PutMapping(path = "/delete/{userId}")
    public ResponseEntity<UserDTO> deleteSuperAdmin(@PathVariable(name="userId") String userId)
    {
        UserDTO superAdminDTO = superAdminService.deleteSuperAdmin(userId);
        return new ResponseEntity<>(superAdminDTO, HttpStatus.OK);
    }


    //Mapping to authorise a super admin

    @PutMapping(path = "/authorise/{userId}")
    public ResponseEntity<UserDTO> authorizeSuperAdmin(@PathVariable(name="userId") String userId)
    {
        UserDTO superAdminDTO = superAdminService.authoriseSuperAdmin(userId);
        return new ResponseEntity<>(superAdminDTO, HttpStatus.OK);
    }



}
