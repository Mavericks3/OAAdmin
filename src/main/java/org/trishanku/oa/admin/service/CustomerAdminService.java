package org.trishanku.oa.admin.service;

import org.trishanku.oa.admin.model.UserDTO;

import java.util.List;

public interface CustomerAdminService {


    List<UserDTO> getAllCustomerAdmins();

    UserDTO getCustomerAdminById(String userId);

    UserDTO addCustomerAdmin(UserDTO userDTO);

    UserDTO modifyCustomerAdmin(String userId, UserDTO userDTO);

    UserDTO authoriseCustomerAdmin(String userId);

    List<UserDTO> getPendingCustomerAdmins();

    UserDTO deleteCustomerAdmin(String userId);
}
