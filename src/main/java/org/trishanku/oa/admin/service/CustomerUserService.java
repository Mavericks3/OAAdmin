package org.trishanku.oa.admin.service;

import org.trishanku.oa.admin.model.UserDTO;

import java.util.List;

public interface CustomerUserService {


    List<UserDTO> getAllCustomerUsers();

    UserDTO getCustomerUserById(String userId);

    UserDTO addCustomerUser(UserDTO userDTO);

    UserDTO modifyCustomerUser(String userId, UserDTO userDTO);

    UserDTO authoriseCustomerUser(String userId);

    List<UserDTO> getPendingCustomerUsers();

    UserDTO deleteCustomerUser(String userId);

    String getNewReference();
}
