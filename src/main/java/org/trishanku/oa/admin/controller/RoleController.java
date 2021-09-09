package org.trishanku.oa.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trishanku.oa.admin.entity.Role;
import org.trishanku.oa.admin.repository.RoleRepository;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    @Autowired
    RoleRepository roleRepository;

    @GetMapping
    public ResponseEntity<List<Role>> getRoles()
    {
       return new ResponseEntity<>(roleRepository.findAll(), HttpStatus.OK);
    }
}
