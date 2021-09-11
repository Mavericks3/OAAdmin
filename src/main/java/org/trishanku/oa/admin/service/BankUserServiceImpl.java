package org.trishanku.oa.admin.service;

import com.netflix.discovery.converters.Auto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.trishanku.oa.admin.entity.Customer;
import org.trishanku.oa.admin.entity.Role;
import org.trishanku.oa.admin.entity.TransactionStatusEnum;
import org.trishanku.oa.admin.entity.User;
import org.trishanku.oa.admin.mapper.RoleMapper;
import org.trishanku.oa.admin.mapper.UserMapper;
import org.trishanku.oa.admin.model.RoleDTO;
import org.trishanku.oa.admin.model.UserDTO;
import org.trishanku.oa.admin.repository.CustomerRepository;
import org.trishanku.oa.admin.repository.RoleRepository;
import org.trishanku.oa.admin.repository.UserRepository;

import java.util.*;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

@Service
@Slf4j
public class BankUserServiceImpl implements BankUserService{

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserMapper userMapper;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public List<UserDTO> getAllBankUsers() {
        List<Role> rolesList = roleRepository.findByNameIn(Arrays.asList("BANK_USER_MAKER","BANK_USER_CHECKER","BANK_USER_VIEWER"));
        for(Role role: rolesList)
        {
            log.debug("Role is " + role);
        }
        List<User> users = userRepository.findByRolesIn(rolesList);
        if(users.size()==0) throw new RuntimeException("There are no bank user's currently in the system");

        //remove duplicates

        List<User> unique = users.stream()
                .collect(collectingAndThen(toCollection(() -> new TreeSet<>(Comparator.comparing(User::getUserId))),
                        ArrayList::new));

        return userMapper.userListToUserDTOList(unique);
    }

    @Override
    public UserDTO getBankUserById(String userId) {
        User user = userRepository.findByRolesInAndUserId(roleRepository.findByNameIn(List.of(new String[]{"BANK_USER_MAKER", "BANK_USER_CHECKER", "BANK_USER_VIEWER"})),userId);
        if(user==null) throw new RuntimeException("Bank user with id " + userId + " does not exist");
        return userMapper.userToUserDTO(user);
    }

    @Override
    public UserDTO addBankUser(UserDTO userDTO) {
        if(userRepository.findByUserId(userDTO.getUserId()).isPresent()) throw new RuntimeException("Bank user with id " + userDTO.getUserId() + " already exists");
        User user = userMapper.userDTOToUser(userDTO);
        user.setUuid(UUID.randomUUID());

        // to set bank admin role
        List<Role> bankUserRoles = new ArrayList<>();
        for(RoleDTO roleDTO: userDTO.getRoles())
        {
            bankUserRoles.add(roleRepository.findByName(roleDTO.getName()));
        }
        user.setRoles(bankUserRoles);

        // to link super admin to bank unit
        List<Customer> bank = new ArrayList<>();
        if(customerRepository.findByBank(true).isEmpty()) throw new RuntimeException("Bank business unit does not exist");
        bank.add(customerRepository.findByBank(true).get());
        user.setCustomers(bank);
        //BELOW LINE TO BE CHANGED TO GET THE USER DETAILS FROM REQUEST
        user.setCreationDetails("RAVIKANTH");
        return userMapper.userToUserDTO(userRepository.save(user));
    }

    @Override
    public UserDTO modifyBankUser(String userId, UserDTO userDTO) {
        if(userRepository.findByUserId(userId).isEmpty()) throw new RuntimeException("Bank user with id " + userId + " does not exist");
        User existingUserDetails = userRepository.findByUserId(userId).get();
        existingUserDetails.setFirstName(userDTO.getFirstName());
        existingUserDetails.setLastName(userDTO.getLastName());
        existingUserDetails.setEffectiveDate(userDTO.getEffectiveDate());
        existingUserDetails.setEmailAddress(userDTO.getEmailAddress());

        //to get role ids
        List<Role> newRoles = new ArrayList<>();
        for(RoleDTO roleDTO:userDTO.getRoles())
        {
            Role roleDetails = roleRepository.findByName(roleDTO.getName());
            if(roleDetails == null) throw new RuntimeException("Role " + roleDTO.getName() + " does not exist");
            newRoles.add(roleDetails);
        }

        existingUserDetails.setRoles(newRoles);
        //BELOW LINE TO BE CHANGED TO GET THE USER DETAILS FROM REQUEST
        existingUserDetails.setModificationDetails("RAVIKANTH");
        User savedUser = userRepository.save(existingUserDetails);
        return userMapper.userToUserDTO(savedUser);
    }

    @Override
    public UserDTO authoriseBankUser(String userId) {
        if(userRepository.findByUserId(userId).isEmpty()) throw new RuntimeException("Bank admin with id " + userId + " does not exist");
        User existingUserDetails = userRepository.findByUserId(userId).get();
        //BELOW LINE TO BE CHANGED TO GET THE USER DETAILS FROM REQUEST
        existingUserDetails.setAuthorizationDetails("RAVIKANTH");
        User savedUser = userRepository.save(existingUserDetails);
        return userMapper.userToUserDTO(savedUser);
    }

    @Override
    public List<UserDTO> getPendingBankUsers() {

        List<Role> rolesList = roleRepository.findByNameIn(Arrays.asList("BANK_USER_MAKER","BANK_USER_CHECKER","BANK_USER_VIEWER"));
        for(Role role: rolesList)
        {
            log.debug("Role is " + role);
        }
        List<User> users = userRepository.findByRolesInAndTransactionStatus(rolesList,TransactionStatusEnum.PENDING);
        if(users.size()==0) throw new RuntimeException("There are no bank user's currently in the system");

        //remove duplicates

        List<User> unique = users.stream()
                .collect(collectingAndThen(toCollection(() -> new TreeSet<>(Comparator.comparing(User::getUserId))),
                        ArrayList::new));

/*        List<User> users = userRepository.findByRolesAndTransactionStatus(roleRepository.findByName("BANK_ADMIN"), TransactionStatusEnum.PENDING);
        if(users.size()==0) throw new RuntimeException("There are no pending bank admin's currently in the system");
        return userMapper.userListToUserDTOList(users);*/
        return userMapper.userListToUserDTOList(unique);
    }

    @Override
    public UserDTO deleteBankUser(String userId) {
        if(userRepository.findByUserId(userId).isEmpty()) throw new RuntimeException("Bank admin with id " + userId + " does not exist");
        User existingUserDetails = userRepository.findByUserId(userId).get();
        existingUserDetails.setStatus(false);
        //BELOW LINE TO BE CHANGED TO GET THE USER DETAILS FROM REQUEST
        existingUserDetails.setModificationDetails("RAVIKANTH");
        User savedUser = userRepository.save(existingUserDetails);
        return userMapper.userToUserDTO(savedUser);
    }
}
