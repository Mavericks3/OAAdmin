package org.trishanku.oa.admin.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.trishanku.oa.admin.entity.Customer;
import org.trishanku.oa.admin.entity.Role;
import org.trishanku.oa.admin.entity.TransactionStatusEnum;
import org.trishanku.oa.admin.entity.User;
import org.trishanku.oa.admin.mapper.UserMapper;
import org.trishanku.oa.admin.model.CustomerDTO;
import org.trishanku.oa.admin.model.UserDTO;
import org.trishanku.oa.admin.repository.CustomerRepository;
import org.trishanku.oa.admin.repository.RoleRepository;
import org.trishanku.oa.admin.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class CustomerAdminServiceImpl implements CustomerAdminService{

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserMapper userMapper;

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public List<UserDTO> getAllCustomerAdmins() {
        List<User> users = userRepository.findByRoles(roleRepository.findByName("CUSTOMER_ADMIN"));
        if(users.size()==0) throw new RuntimeException("There are no customer admin's currently in the system");
        return userMapper.userListToUserDTOList(users);
    }

    @Override
    public UserDTO getCustomerAdminById(String userId) {
        User user = userRepository.findByRolesAndUserId(roleRepository.findByName("CUSTOMER_ADMIN"),userId);
        if(user==null) throw new RuntimeException("Customer admin with id " + userId + " does not exist");
        return userMapper.userToUserDTO(user);
    }

    @Override
    public UserDTO addCustomerAdmin(UserDTO userDTO) {
        if(userRepository.findByUserId(userDTO.getUserId()).isPresent()) throw new RuntimeException("Customer admin with id " + userDTO.getUserId() + " already exists");
        User user = userMapper.userDTOToUser(userDTO);
        user.setUuid(UUID.randomUUID());

        // to set customer admin role
        List<Role> customerAdminRoles = new ArrayList<>();
        customerAdminRoles.add(roleRepository.findByName("CUSTOMER_ADMIN"));
        user.setRoles(customerAdminRoles);

        // to link customer admin to customer unit
        List<Customer> customer = new ArrayList<>();

        for(CustomerDTO customerDTO: userDTO.getCustomers()) {
            if (customerRepository.findByCustomerId(customerDTO.getCustomerId()).isEmpty())
                throw new RuntimeException("Customer business unit does not exist");
            customer.add(customerRepository.findByCustomerId(customerDTO.getCustomerId()).get());
            user.setCustomers(customer);
        }
        //BELOW LINE TO BE CHANGED TO GET THE USER DETAILS FROM REQUEST
        user.setCreationDetails("RAVIKANTH");
        return userMapper.userToUserDTO(userRepository.save(user));
    }

    @Override
    public UserDTO modifyCustomerAdmin(String userId, UserDTO userDTO) {
        if(userRepository.findByUserId(userId).isEmpty()) throw new RuntimeException("Customer admin with id " + userId + " does not exist");
        User existingUserDetails = userRepository.findByUserId(userId).get();
        existingUserDetails.setFirstName(userDTO.getFirstName());
        existingUserDetails.setLastName(userDTO.getLastName());
        existingUserDetails.setEffectiveDate(userDTO.getEffectiveDate());
        existingUserDetails.setEmailAddress(userDTO.getEmailAddress());

        // to link customer admin to customer unit
        List<Customer> customer = new ArrayList<>();

        for(CustomerDTO customerDTO: userDTO.getCustomers()) {
            if (customerRepository.findByCustomerId(customerDTO.getCustomerId()).isEmpty())
                throw new RuntimeException("Customer business unit does not exist");
            customer.add(customerRepository.findByCustomerId(customerDTO.getCustomerId()).get());
            existingUserDetails.setCustomers(customer);
        }
        //BELOW LINE TO BE CHANGED TO GET THE USER DETAILS FROM REQUEST
        existingUserDetails.setModificationDetails("RAVIKANTH");
        User savedUser = userRepository.save(existingUserDetails);
        return userMapper.userToUserDTO(savedUser);
    }

    @Override
    public UserDTO authoriseCustomerAdmin(String userId) {
        if(userRepository.findByUserId(userId).isEmpty()) throw new RuntimeException("Customer admin with id " + userId + " does not exist");
        User existingUserDetails = userRepository.findByUserId(userId).get();
        //BELOW LINE TO BE CHANGED TO GET THE USER DETAILS FROM REQUEST
        existingUserDetails.setAuthorizationDetails("RAVIKANTH");
        User savedUser = userRepository.save(existingUserDetails);
        return userMapper.userToUserDTO(savedUser);
    }

    @Override
    public List<UserDTO> getPendingCustomerAdmins() {
        List<User> users = userRepository.findByRolesAndTransactionStatus(roleRepository.findByName("CUSTOMER_ADMIN"), TransactionStatusEnum.PENDING);
        if(users.size()==0) throw new RuntimeException("There are no pending customer admin's currently in the system");
        return userMapper.userListToUserDTOList(users);
    }

    @Override
    public UserDTO deleteCustomerAdmin(String userId) {
        if(userRepository.findByUserId(userId).isEmpty()) throw new RuntimeException("Customer admin with id " + userId + " does not exist");
        User existingUserDetails = userRepository.findByUserId(userId).get();
        existingUserDetails.setStatus(false);
        //BELOW LINE TO BE CHANGED TO GET THE USER DETAILS FROM REQUEST
        existingUserDetails.setModificationDetails("RAVIKANTH");
        User savedUser = userRepository.save(existingUserDetails);
        return userMapper.userToUserDTO(savedUser);
    }
}
