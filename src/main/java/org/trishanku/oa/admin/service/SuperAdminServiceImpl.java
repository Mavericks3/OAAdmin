package org.trishanku.oa.admin.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.trishanku.oa.admin.entity.Role;
import org.trishanku.oa.admin.entity.User;
import org.trishanku.oa.admin.mapper.UserMapper;
import org.trishanku.oa.admin.model.RoleDTO;
import org.trishanku.oa.admin.model.UserDTO;
import org.trishanku.oa.admin.repository.RoleRepository;
import org.trishanku.oa.admin.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SuperAdminServiceImpl implements SuperAdminService{

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserMapper userMapper;

    @Override
    public List<UserDTO> getAllSuperAdmins() {
        List<User> users = userRepository.findByRoles(roleRepository.findByName("SUPER_ADMIN"));
        return userMapper.UserListToUserDTOList(users);
    }
}
