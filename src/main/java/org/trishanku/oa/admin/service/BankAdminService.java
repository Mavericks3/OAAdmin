package org.trishanku.oa.admin.service;

import org.trishanku.oa.admin.model.UserDTO;

import java.util.List;

public interface BankAdminService {


    List<UserDTO> getAllBankAdmins();

    UserDTO getBankAdminById(String userId);

    UserDTO addBankAdmin(UserDTO userDTO);

    UserDTO modifyBankAdmin(String userId, UserDTO userDTO);

    UserDTO authoriseBankAdmin(String userId);

    List<UserDTO> getPendingBankAdmins();

    UserDTO deleteBankAdmin(String userId);
}
