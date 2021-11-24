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
import org.trishanku.oa.admin.model.CustomerDTO;
import org.trishanku.oa.admin.model.UserDTO;
import org.trishanku.oa.admin.repository.CustomerRepository;
import org.trishanku.oa.admin.repository.RoleRepository;
import org.trishanku.oa.admin.repository.UserRepository;

import java.util.*;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

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
    JWTUtil jwtUtil;

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public List<UserDTO> getAllCustomerAdmins() {
        List<User> users = userRepository.findByRolesIn(roleRepository.findByNameIn(Arrays.asList("CORPORATE_ADMIN_MAKER","CORPORATE_ADMIN_CHECKER","CORPORATE_ADMIN_VIEWER")));
        if(users.size()==0) throw new RuntimeException("There are no customer admin's currently in the system");
        // to remove duplicates
        List<User> distinctUsers = users.stream()
                .collect(collectingAndThen(toCollection(() -> new TreeSet<>(Comparator.comparing(User::getUserId))),
                        ArrayList::new));

        return userMapper.userListToUserDTOList(distinctUsers);
    }

    @Override
    public UserDTO getCustomerAdminById(String userId) {
        User user = userRepository.findByRolesInAndUserId(roleRepository.findByNameIn(Arrays.asList("CORPORATE_ADMIN_MAKER","CORPORATE_ADMIN_CHECKER","CORPORATE_ADMIN_VIEWER")),userId);
        if(user==null) throw new RuntimeException("Customer admin with id " + userId + " does not exist");
        return userMapper.userToUserDTO(user);
    }

    @Override
    public UserDTO addCustomerAdmin(UserDTO userDTO) {
        if(userRepository.findByUserId(userDTO.getUserId()).isPresent()) throw new RuntimeException("Customer admin with id " + userDTO.getUserId() + " already exists");
        User user = userMapper.userDTOToUser(userDTO);
        user.setUuid(UUID.randomUUID());


        // to set corporate admin roles
        userDTO.getRoles().removeIf(roleDTO -> !
                (roleDTO.getName().equalsIgnoreCase("CORPORATE_ADMIN_MAKER")||
                        roleDTO.getName().equalsIgnoreCase("CORPORATE_ADMIN_CHECKER")
                        ||
                        roleDTO.getName().equalsIgnoreCase("CORPORATE_ADMIN_VIEWER")));

        List<Role> customerAdminRoles = new ArrayList<>();
        userDTO.getRoles().forEach(roleDTO -> customerAdminRoles.add(roleRepository.findByName(roleDTO.getName())));
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
        user.setCreationDetails(jwtUtil.extractUsernameFromRequest());
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

        // to set bank admin roles
        userDTO.getRoles().removeIf(roleDTO -> !
                (roleDTO.getName().equalsIgnoreCase("CORPORATE_ADMIN_MAKER")||
                        roleDTO.getName().equalsIgnoreCase("CORPORATE_ADMIN_CHECKER")
                        ||
                        roleDTO.getName().equalsIgnoreCase("CORPORATE_ADMIN_VIEWER")));

        List<Role> customerAdminRoles = new ArrayList<>();
        userDTO.getRoles().forEach(roleDTO -> customerAdminRoles.add(roleRepository.findByName(roleDTO.getName())));

        existingUserDetails.setRoles(customerAdminRoles);

        existingUserDetails.setStatus(userDTO.isStatus());

        existingUserDetails.setModificationDetails(jwtUtil.extractUsernameFromRequest());
        User savedUser = userRepository.save(existingUserDetails);
        return userMapper.userToUserDTO(savedUser);
    }

    @Override
    @Transactional
    public UserDTO authoriseCustomerAdmin(String userId) {
        if(userRepository.findByUserId(userId).isEmpty()) throw new RuntimeException("Customer admin with id " + userId + " does not exist");
        User existingUserDetails = userRepository.findByUserId(userId).get();

        existingUserDetails.setAuthorizationDetails(jwtUtil.extractUsernameFromRequest());
        User savedUser = userRepository.save(existingUserDetails);
        if(savedUser.isDeleteFlag()) userRepository.delete(savedUser);
        return userMapper.userToUserDTO(savedUser);
    }

    @Override
    public List<UserDTO> getPendingCustomerAdmins() {
        List<User> users = userRepository.findByRolesInAndTransactionStatus(roleRepository.findByNameIn(Arrays.asList("CORPORATE_ADMIN_MAKER","CORPORATE_ADMIN_CHECKER","CORPORATE_ADMIN_VIEWER")), TransactionStatusEnum.PENDING);
        if(users.size()==0) throw new RuntimeException("There are no pending customer admin's currently in the system");
        // to remove duplicates
        List<User> distinctUsers = users.stream()
                .collect(collectingAndThen(toCollection(() -> new TreeSet<>(Comparator.comparing(User::getUserId))),
                        ArrayList::new));
        return userMapper.userListToUserDTOList(distinctUsers);
    }

    @Override
    public UserDTO deleteCustomerAdmin(String userId) {
        if(userRepository.findByUserId(userId).isEmpty()) throw new RuntimeException("Customer admin with id " + userId + " does not exist");
        User existingUserDetails = userRepository.findByUserId(userId).get();
        existingUserDetails.setDeleteFlag(true);
        existingUserDetails.setModificationDetails(jwtUtil.extractUsernameFromRequest());
        User savedUser = userRepository.save(existingUserDetails);
        return userMapper.userToUserDTO(savedUser);
    }
}
