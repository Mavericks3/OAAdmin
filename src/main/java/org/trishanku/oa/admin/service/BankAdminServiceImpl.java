package org.trishanku.oa.admin.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.trishanku.oa.admin.entity.Customer;
import org.trishanku.oa.admin.entity.Role;
import org.trishanku.oa.admin.entity.TransactionStatusEnum;
import org.trishanku.oa.admin.entity.User;
import org.trishanku.oa.admin.jwtauthentication.configuration.service.JWTUtil;
import org.trishanku.oa.admin.mapper.UserMapper;
import org.trishanku.oa.admin.model.UserDTO;
import org.trishanku.oa.admin.repository.CustomerRepository;
import org.trishanku.oa.admin.repository.RoleRepository;
import org.trishanku.oa.admin.repository.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

@Service
@Slf4j
public class BankAdminServiceImpl implements BankAdminService{

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
    public List<UserDTO> getAllBankAdmins() {
        List<User> users = userRepository.findByRolesIn(roleRepository.findByNameIn(Arrays.asList("BANK_ADMIN_MAKER","BANK_ADMIN_CHECKER","BANK_ADMIN_VIEWER")));
        if(users.size()==0)
            throw new RuntimeException("There are no bank admin's currently in the system");
      // to remove duplicates
        List<User> distinctUsers = users.stream()
                .collect(collectingAndThen(toCollection(() -> new TreeSet<>(Comparator.comparing(User::getUserId))),
                        ArrayList::new));

        return userMapper.userListToUserDTOList(distinctUsers);
    }

    @Override
    public UserDTO getBankAdminById(String userId) {
        User user = userRepository.findByRolesInAndUserId(roleRepository.findByNameIn(Arrays.asList("BANK_ADMIN_MAKER","BANK_ADMIN_CHECKER","BANK_ADMIN_VIEWER")),userId);
        if(user==null) throw new RuntimeException("Bank admin with id " + userId + " does not exist");
        else if (user.isStatus()==false && user.getTransactionStatus()==TransactionStatusEnum.PENDING) throw new RuntimeException("Bank admin with id " + userId + " does not exist");
        return userMapper.userToUserDTO(user);
    }

    @Override
    public UserDTO addBankAdmin(UserDTO userDTO) {

        if(userRepository.findByUserId(userDTO.getUserId()).isPresent()) throw new RuntimeException("Bank admin with id " + userDTO.getUserId() + " already exists");
        User user = userMapper.userDTOToUser(userDTO);
        user.setUuid(UUID.randomUUID());

        // to set bank admin roles
        userDTO.getRoles().removeIf(roleDTO -> !
                (roleDTO.getName().equalsIgnoreCase("BANK_ADMIN_MAKER")||
                        roleDTO.getName().equalsIgnoreCase("BANK_ADMIN_CHECKER")
                        ||
                        roleDTO.getName().equalsIgnoreCase("BANK_ADMIN_VIEWER")));

        List<Role> bankAdminRoles = new ArrayList<>();
        userDTO.getRoles().forEach(roleDTO -> bankAdminRoles.add(roleRepository.findByName(roleDTO.getName())));
        //bankAdminRoles.add(roleRepository.findByName("BANK_ADMIN"));
        user.setRoles(bankAdminRoles);

        // to link bank admin to bank unit
        List<Customer> bank = new ArrayList<>();
        if(customerRepository.findByBank(true).isEmpty()) throw new RuntimeException("Bank business unit does not exist");
        bank.add(customerRepository.findByBank(true).get());
        user.setCustomers(bank);
        user.setCreationDetails(jwtUtil.extractUsernameFromRequest());
        return userMapper.userToUserDTO(userRepository.save(user));
    }

    @Override
    public UserDTO modifyBankAdmin(String userId, UserDTO userDTO) {
        if(userRepository.findByUserId(userId).isEmpty()) throw new RuntimeException("Bank admin with id " + userId + " does not exist");
        User existingUserDetails = userRepository.findByUserId(userId).get();
        existingUserDetails.setFirstName(userDTO.getFirstName());
        existingUserDetails.setLastName(userDTO.getLastName());
        existingUserDetails.setEffectiveDate(userDTO.getEffectiveDate());
        existingUserDetails.setEmailAddress(userDTO.getEmailAddress());

        // to set bank admin roles
        userDTO.getRoles().removeIf(roleDTO -> !
                (roleDTO.getName().equalsIgnoreCase("BANK_ADMIN_MAKER")||
                        roleDTO.getName().equalsIgnoreCase("BANK_ADMIN_CHECKER")
                        ||
                        roleDTO.getName().equalsIgnoreCase("BANK_ADMIN_VIEWER")));

        List<Role> bankAdminRoles = new ArrayList<>();
        userDTO.getRoles().forEach(roleDTO -> bankAdminRoles.add(roleRepository.findByName(roleDTO.getName())));

        existingUserDetails.setRoles(bankAdminRoles);
        existingUserDetails.setStatus(userDTO.isStatus());
        existingUserDetails.setExpiryDate(userDTO.getExpiryDate());

        existingUserDetails.setModificationDetails(jwtUtil.extractUsernameFromRequest());
        User savedUser = userRepository.save(existingUserDetails);
        return userMapper.userToUserDTO(savedUser);
    }

    @Override
    @Transactional
    public UserDTO authoriseBankAdmin(String userId) {
        if(userRepository.findByUserId(userId).isEmpty()) throw new RuntimeException("Bank admin with id " + userId + " does not exist");
        User existingUserDetails = userRepository.findByUserId(userId).get();

        existingUserDetails.setAuthorizationDetails(jwtUtil.extractUsernameFromRequest());
        User savedUser = userRepository.save(existingUserDetails);
        // to delete the user if marked for deletion
        if(savedUser.isDeleteFlag()) userRepository.delete(savedUser);
        return userMapper.userToUserDTO(savedUser);
    }

    @Override
    public List<UserDTO> getPendingBankAdmins() {
        List<User> users = userRepository.findByRolesInAndTransactionStatus(roleRepository.findByNameIn(Arrays.asList("BANK_ADMIN_MAKER","BANK_ADMIN_CHECKER","BANK_ADMIN_VIEWER")), TransactionStatusEnum.PENDING);
        if(users.size()==0) throw new RuntimeException("There are no pending bank admin's currently in the system");
        // to remove duplicates
        List<User> distinctUsers = users.stream()
                .collect(collectingAndThen(toCollection(() -> new TreeSet<>(Comparator.comparing(User::getUserId))),
                        ArrayList::new));
        return userMapper.userListToUserDTOList(distinctUsers);
    }

    @Override
    public UserDTO deleteBankAdmin(String userId) {
        if(userRepository.findByUserId(userId).isEmpty()) throw new RuntimeException("Bank admin with id " + userId + " does not exist");
        User existingUserDetails = userRepository.findByUserId(userId).get();
        existingUserDetails.setDeleteFlag(true);

        existingUserDetails.setModificationDetails(jwtUtil.extractUsernameFromRequest());
        User savedUser = userRepository.save(existingUserDetails);
        return userMapper.userToUserDTO(savedUser);
    }
}
