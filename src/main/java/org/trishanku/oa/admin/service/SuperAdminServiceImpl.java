package org.trishanku.oa.admin.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.trishanku.oa.admin.entity.Role;
import org.trishanku.oa.admin.entity.User;
import org.trishanku.oa.admin.mapper.UserMapper;
import org.trishanku.oa.admin.model.UserDTO;
import org.trishanku.oa.admin.repository.RoleRepository;
import org.trishanku.oa.admin.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        return userMapper.userListToUserDTOList(users);
    }

    @Override
    public UserDTO getSuperAdminById(String userId) {
        User user = userRepository.findByRolesAndUserId(roleRepository.findByName("SUPER_ADMIN"),userId);
        if(user==null) throw new RuntimeException("user with id -> " + userId + " does not exist");
        return userMapper.userToUserDTO(user);
    }

    @Override
    public UserDTO addSuperAdmin(UserDTO userDTO) {
        User user = userMapper.userDTOToUser(userDTO);
        user.setUuid(UUID.randomUUID());
        List<Role> superAdminRoles = new ArrayList<>();
        superAdminRoles.add(roleRepository.findByName("SUPER_ADMIN"));
        user.setRoles(superAdminRoles);
        return userMapper.userToUserDTO(userRepository.save(user));
    }
}
