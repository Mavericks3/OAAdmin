package org.trishanku.oa.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trishanku.oa.admin.entity.Role;
import org.trishanku.oa.admin.mapper.RoleMapper;
import org.trishanku.oa.admin.model.RoleDTO;
import org.trishanku.oa.admin.repository.RoleRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RoleMapper roleMapper;

    @GetMapping
    @Transactional
    public ResponseEntity<List<RoleDTO>> getRoles()
    {
        List<Role> roleList = roleRepository.findAll();
        List<RoleDTO> roleDTOS = new ArrayList<>();
        roleList.forEach((role)-> roleDTOS.add(roleMapper.roleToRoleDTO(role)));
       return new ResponseEntity<>(roleDTOS, HttpStatus.OK);
    }
}
