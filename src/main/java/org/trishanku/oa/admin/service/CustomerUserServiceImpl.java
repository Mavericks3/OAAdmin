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
public class CustomerUserServiceImpl implements CustomerUserService{

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserMapper userMapper;
    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    CustomerRepository customerRepository;

    private static final List<String> corporateUsersRoles =  Arrays.asList("CORPORATE_USER_MAKER","CORPORATE_USER_CHECKER","CORPORATE_USER_VIEWER");

    @Override
    public List<UserDTO> getAllCustomerUsers() {
        List<Role> rolesList = roleRepository.findByNameIn(corporateUsersRoles);
        for(Role role: rolesList)
        {
            log.debug("Role is " + role);
        }
        List<User> users = userRepository.findByRolesIn(rolesList);
        if(users.size()==0) throw new RuntimeException("There are no customer user's currently in the system");

        //remove duplicates

        List<User> unique = users.stream()
                .collect(collectingAndThen(toCollection(() -> new TreeSet<>(Comparator.comparing(User::getUserId))),
                        ArrayList::new));

        return userMapper.userListToUserDTOList(unique);
    }

    @Override
    public UserDTO getCustomerUserById(String userId) {
        User user = userRepository.findByRolesInAndUserId(roleRepository.findByNameIn(corporateUsersRoles),userId);
        if(user==null) throw new RuntimeException("Customer user with id " + userId + " does not exist");
        return userMapper.userToUserDTO(user);
    }

    @Override
    public UserDTO addCustomerUser(UserDTO userDTO) {
        if(userRepository.findByUserId(userDTO.getUserId()).isPresent()) throw new RuntimeException("Customer user with id " + userDTO.getUserId() + " already exists");
        User user = userMapper.userDTOToUser(userDTO);
        user.setUuid(UUID.randomUUID());

        // to set customer user role
        userDTO.getRoles().removeIf(roleDTO -> !
                (roleDTO.getName().equalsIgnoreCase("CORPORATE_USER_MAKER")||
                        roleDTO.getName().equalsIgnoreCase("CORPORATE_USER_CHECKER")
                        ||
                        roleDTO.getName().equalsIgnoreCase("CORPORATE_USER_VIEWER")));


        List<Role> customerUserRoles = new ArrayList<>();
        for(RoleDTO roleDTO: userDTO.getRoles())
        {
            customerUserRoles.add(roleRepository.findByName(roleDTO.getName()));
        }
        user.setRoles(customerUserRoles);

        // to link user to customer unit
        List<Customer> customer = new ArrayList<>();
        if(customerRepository.findByCustomerIdAndBank(userDTO.getCustomers().get(0).getCustomerId(),false).isEmpty()) throw new RuntimeException("Customer business unit does not exist");
        customer.add(customerRepository.findByCustomerIdAndBank(userDTO.getCustomers().get(0).getCustomerId(),false).get());
        user.setCustomers(customer);
        user.setCreationDetails(jwtUtil.extractUsernameFromRequest());
        return userMapper.userToUserDTO(userRepository.save(user));
    }

    @Override
    public UserDTO modifyCustomerUser(String userId, UserDTO userDTO) {
        if(userRepository.findByUserId(userId).isEmpty()) throw new RuntimeException("Customer user with id " + userId + " does not exist");
        User existingUserDetails = userRepository.findByUserId(userId).get();
        existingUserDetails.setFirstName(userDTO.getFirstName());
        existingUserDetails.setLastName(userDTO.getLastName());
        existingUserDetails.setEffectiveDate(userDTO.getEffectiveDate());
        existingUserDetails.setEmailAddress(userDTO.getEmailAddress());
        existingUserDetails.setStatus(userDTO.isStatus());
        existingUserDetails.setExpiryDate(userDTO.getExpiryDate());

        // to set customer user role
        userDTO.getRoles().removeIf(roleDTO -> !
                (roleDTO.getName().equalsIgnoreCase("CORPORATE_USER_MAKER")||
                        roleDTO.getName().equalsIgnoreCase("CORPORATE_USER_CHECKER")
                        ||
                        roleDTO.getName().equalsIgnoreCase("CORPORATE_USER_VIEWER")));

        //to get role ids
        List<Role> newRoles = new ArrayList<>();
        for(RoleDTO roleDTO:userDTO.getRoles())
        {
            Role roleDetails = roleRepository.findByName(roleDTO.getName());
            if(roleDetails == null) throw new RuntimeException("Role " + roleDTO.getName() + " does not exist");
            newRoles.add(roleDetails);
        }

        // to link user to customer unit
        List<Customer> customer = new ArrayList<>();
        if(customerRepository.findByCustomerIdAndBank(userDTO.getCustomers().get(0).getCustomerId(),false).isEmpty()) throw new RuntimeException("Customer business unit does not exist");
        customer.add(customerRepository.findByCustomerIdAndBank(userDTO.getCustomers().get(0).getCustomerId(),false).get());
        existingUserDetails.setCustomers(customer);

        existingUserDetails.setRoles(newRoles);

        existingUserDetails.setModificationDetails(jwtUtil.extractUsernameFromRequest());
        User savedUser = userRepository.save(existingUserDetails);
        return userMapper.userToUserDTO(savedUser);
    }

    @Override
    @Transactional
    public UserDTO authoriseCustomerUser(String userId) {
        if(userRepository.findByUserId(userId).isEmpty()) throw new RuntimeException("Customer admin with id " + userId + " does not exist");
        User existingUserDetails = userRepository.findByUserId(userId).get();

        existingUserDetails.setAuthorizationDetails(jwtUtil.extractUsernameFromRequest());
        User savedUser = userRepository.save(existingUserDetails);
        if(savedUser.isDeleteFlag()) userRepository.delete(savedUser);
        return userMapper.userToUserDTO(savedUser);
    }

    @Override
    public List<UserDTO> getPendingCustomerUsers() {

        List<Role> rolesList = roleRepository.findByNameIn(corporateUsersRoles);
        for(Role role: rolesList)
        {
            log.debug("Role is " + role);
        }
        List<User> users = userRepository.findByRolesInAndTransactionStatus(rolesList,TransactionStatusEnum.PENDING);
        if(users.size()==0) throw new RuntimeException("There are no customer user's currently in the system");

        //remove duplicates

        List<User> unique = users.stream()
                .collect(collectingAndThen(toCollection(() -> new TreeSet<>(Comparator.comparing(User::getUserId))),
                        ArrayList::new));


        return userMapper.userListToUserDTOList(unique);
    }

    @Override
    public UserDTO deleteCustomerUser(String userId) {
        if(userRepository.findByUserId(userId).isEmpty()) throw new RuntimeException("Customer admin with id " + userId + " does not exist");
        User existingUserDetails = userRepository.findByUserId(userId).get();
        existingUserDetails.setDeleteFlag(true);
        existingUserDetails.setModificationDetails(jwtUtil.extractUsernameFromRequest());
        User savedUser = userRepository.save(existingUserDetails);
        return userMapper.userToUserDTO(savedUser);
    }

    @Override
    public String getNewReference() {

        String customerUserSequence = userRepository.getCustomerUserSequence();
        return "CustomerUser" + Calendar.getInstance().get(Calendar.YEAR) + customerUserSequence;
    }
}
