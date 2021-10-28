package org.trishanku.oa.admin.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.trishanku.oa.admin.entity.Customer;
import org.trishanku.oa.admin.entity.Role;
import org.trishanku.oa.admin.entity.TransactionStatusEnum;
import org.trishanku.oa.admin.entity.User;
import org.trishanku.oa.admin.exception.ResourceAlreadyExistsException;
import org.trishanku.oa.admin.jwtauthentication.configuration.service.JWTUtil;
import org.trishanku.oa.admin.mapper.UserMapper;
import org.trishanku.oa.admin.model.UserDTO;
import org.trishanku.oa.admin.notification.entity.NotificationEvent;
import org.trishanku.oa.admin.notification.service.NotificationService;
import org.trishanku.oa.admin.repository.CustomerRepository;
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

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public List<UserDTO> getAllSuperAdmins() {
        List<User> users = userRepository.findByRoles(roleRepository.findByName("SUPER_ADMIN"));
        if(users.size()==0) throw new RuntimeException("There are no super admin's currently in the system");
        return userMapper.userListToUserDTOList(users);
    }

    @Override
    public UserDTO getSuperAdminById(String userId) {
        User user = userRepository.findByRolesAndUserId(roleRepository.findByName("SUPER_ADMIN"),userId);
        if(user==null) throw new RuntimeException("Super admin with id " + userId + " does not exist");
        return userMapper.userToUserDTO(user);
    }


    @Override
    public UserDTO addSuperAdmin(UserDTO userDTO) throws ResourceAlreadyExistsException {
        if(userRepository.findByUserId(userDTO.getUserId()).isPresent()) throw new ResourceAlreadyExistsException("Super admin with id " + userDTO.getUserId() + " already exists");
        User user = userMapper.userDTOToUser(userDTO);
        user.setUuid(UUID.randomUUID());

        // to set super admin role
        List<Role> superAdminRoles = new ArrayList<>();
        superAdminRoles.add(roleRepository.findByName("SUPER_ADMIN"));
        user.setRoles(superAdminRoles);

        // to link super admin to bank unit
        List<Customer> bank = new ArrayList<>();
        if(customerRepository.findByBank(true).isEmpty()) throw new RuntimeException("Bank business unit does not exist");
        bank.add(customerRepository.findByBank(true).get());
        user.setCustomers(bank);
        user.setCreationDetails(jwtUtil.extractUsernameFromRequest());
        return userMapper.userToUserDTO(userRepository.save(user));
    }

    @Override
    public UserDTO modifySuperAdmin(String userId, UserDTO userDTO) {
        if(userRepository.findByUserId(userId).isEmpty()) throw new RuntimeException("Super admin with id " + userId + " does not exist");
        User existingUserDetails = userRepository.findByUserId(userId).get();
        existingUserDetails.setFirstName(userDTO.getFirstName());
        existingUserDetails.setLastName(userDTO.getLastName());
        existingUserDetails.setEffectiveDate(userDTO.getEffectiveDate());
        existingUserDetails.setEmailAddress(userDTO.getEmailAddress());
        existingUserDetails.setModificationDetails(jwtUtil.extractUsernameFromRequest());
        User savedUser = userRepository.save(existingUserDetails);
        return userMapper.userToUserDTO(savedUser);
    }

    @Override
    public UserDTO authoriseSuperAdmin(String userId) {
        if(userRepository.findByUserId(userId).isEmpty()) throw new RuntimeException("Super admin with id " + userId + " does not exist");
        User existingUserDetails = userRepository.findByUserId(userId).get();
        existingUserDetails.setAuthorizationDetails(jwtUtil.extractUsernameFromRequest());
        User savedUser = userRepository.save(existingUserDetails);
        return userMapper.userToUserDTO(savedUser);
    }

    @Override
    public List<UserDTO> getPendingSuperAdmins() {
        List<User> users = userRepository.findByRolesAndTransactionStatus(roleRepository.findByName("SUPER_ADMIN"), TransactionStatusEnum.PENDING);
        if(users.size()==0) throw new RuntimeException("There are no pending super admin's currently in the system");
        return userMapper.userListToUserDTOList(users);
    }

    @Override
    public UserDTO deleteSuperAdmin(String userId) {
        if(userRepository.findByUserId(userId).isEmpty()) throw new RuntimeException("Super admin with id " + userId + " does not exist");
        User existingUserDetails = userRepository.findByUserId(userId).get();
        existingUserDetails.setStatus(false);
        existingUserDetails.setModificationDetails(jwtUtil.extractUsernameFromRequest());
        User savedUser = userRepository.save(existingUserDetails);
        return userMapper.userToUserDTO(savedUser);
    }
}
