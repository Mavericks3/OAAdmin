package org.trishanku.oa.admin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.trishanku.oa.admin.exception.ResourceAlreadyExistsException;
import org.trishanku.oa.admin.model.UserDTO;
import org.trishanku.oa.admin.service.SuperAdminService;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/superadmins")
@Slf4j
public class SuperAdminController {

    @Autowired
    SuperAdminService superAdminService;

    //Mapping to get all super admins
    @GetMapping
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @Transactional
    public ResponseEntity<List<UserDTO>> getAllSuperAdmins()
    {
       // Authentication auth = SecurityContextHolder.getContext().getAuthentication();
       // if(auth!=null) auth.getAuthorities().stream().forEach(grantedAuthority -> log.info("authority of the user" + grantedAuthority.getAuthority()));
        List<UserDTO> superAdminDTOS = superAdminService.getAllSuperAdmins();
        return new ResponseEntity<>(superAdminDTOS, HttpStatus.OK);
    }


    //Mapping to get reference
    @GetMapping(path = "/getNewReference")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @Transactional
    public ResponseEntity<String> getNewReference()
    {

        String newReference= superAdminService.getNewReference();
        return new ResponseEntity<>(newReference, HttpStatus.OK);
    }

    //Mapping to get all pending super admins
    @GetMapping(path = "/pending")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @Transactional
    public ResponseEntity<List<UserDTO>> getPendingSuperAdmins()
    {
        List<UserDTO> superAdminDTOS = superAdminService.getPendingSuperAdmins();
        return new ResponseEntity<>(superAdminDTOS, HttpStatus.OK);
    }

    //Mapping to get a specific super admin
    @GetMapping(path = "/{userId}")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @Transactional
    public ResponseEntity<UserDTO> getSuperAdminById(@PathVariable(name="userId") String userId)
    {
        UserDTO superAdminDTO = superAdminService.getSuperAdminById(userId);
        return new ResponseEntity<>(superAdminDTO, HttpStatus.OK);
    }

    //Mapping to add a super admin

    @PostMapping()
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @Transactional
    public ResponseEntity<UserDTO> addSuperAdmin(@RequestBody UserDTO userDTO) throws ResourceAlreadyExistsException {
        UserDTO superAdminDTO = superAdminService.addSuperAdmin(userDTO);
        return new ResponseEntity<>(superAdminDTO, HttpStatus.OK);
    }

    //Mapping to modify a super admin

    @PutMapping(path = "/{userId}")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @Transactional
    public ResponseEntity<UserDTO> modifySuperAdmin(@PathVariable(name="userId") String userId, @RequestBody UserDTO userDTO)
    {
        UserDTO superAdminDTO = superAdminService.modifySuperAdmin(userId, userDTO);
        return new ResponseEntity<>(superAdminDTO, HttpStatus.OK);
    }

    //Mapping to delete a super admin
    @PutMapping(path = "/activate/{userId}")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @Transactional
    public ResponseEntity<UserDTO> activateSuperAdmin(@PathVariable(name="userId") String userId, @RequestBody UserDTO userDTO)
    {
        UserDTO superAdminDTO = superAdminService.activateSuperAdmin(userId, userDTO);
        return new ResponseEntity<>(superAdminDTO, HttpStatus.OK);
    }

    //Mapping to delete a super admin
    @PutMapping(path = "/delete/{userId}")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @Transactional
    public ResponseEntity<UserDTO> deleteSuperAdmin(@PathVariable(name="userId") String userId)
    {
        UserDTO superAdminDTO = superAdminService.deleteSuperAdmin(userId);
        return new ResponseEntity<>(superAdminDTO, HttpStatus.OK);
    }



    //Mapping to authorise a super admin

    @PutMapping(path = "/authorise/{userId}")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @Transactional
    public ResponseEntity<UserDTO> authorizeSuperAdmin(@PathVariable(name="userId") String userId)
    {
        UserDTO superAdminDTO = superAdminService.authoriseSuperAdmin(userId);
        return new ResponseEntity<>(superAdminDTO, HttpStatus.OK);
    }



}
