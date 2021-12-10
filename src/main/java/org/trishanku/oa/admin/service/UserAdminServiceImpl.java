package org.trishanku.oa.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.trishanku.oa.admin.controller.UserController;
import org.trishanku.oa.admin.entity.User;
import org.trishanku.oa.admin.mapper.UserMapper;
import org.trishanku.oa.admin.model.UserDTO;
import org.trishanku.oa.admin.repository.UserRepository;

import java.util.Optional;

@Service
public class UserAdminServiceImpl implements UserAdminService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    @Override
    public UserDTO getProfileDetails(String userEmailAddress) {
        return userMapper.userToUserDTO(userRepository.findByEmailAddress(userEmailAddress).orElseThrow(() -> new ResourceNotFoundException("User details not found")));
    }

    @Override
    public User getUserDetails(String emailAddress) {
        return userRepository.findByEmailAddress(emailAddress).orElseThrow(() -> new ResourceNotFoundException("User details not found"));
    }
}
