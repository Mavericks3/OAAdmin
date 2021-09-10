package org.trishanku.oa.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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


    //Mapping to get a specific super admin
    @GetMapping(path = "/{userId}")
    public ResponseEntity<UserDTO> getAllSuperAdmins(@PathVariable(name="userId") String userId)
    {
        UserDTO superAdminDTO = superAdminService.getSuperAdminById(userId);
        return new ResponseEntity<>(superAdminDTO, HttpStatus.OK);
    }

    //Mapping to add a super admin

    //Mapping to modify a super admin

    //Mapping to delete a super admin

    //Mapping to suspend a super admin


}
