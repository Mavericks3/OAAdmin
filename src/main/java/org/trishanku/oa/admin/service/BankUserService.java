package org.trishanku.oa.admin.service;

import org.trishanku.oa.admin.model.UserDTO;

import java.util.List;

public interface BankUserService {


    List<UserDTO> getAllBankUsers();

    UserDTO getBankUserById(String userId);

    UserDTO addBankUser(UserDTO userDTO);

    UserDTO modifyBankUser(String userId, UserDTO userDTO);

    UserDTO authoriseBankUser(String userId);

    List<UserDTO> getPendingBankUsers();

    UserDTO deleteBankUser(String userId);
}
