package org.trishanku.oa.admin.service;

import org.trishanku.oa.admin.model.UserDTO;

import java.util.List;

public interface SuperAdminService {


    List<UserDTO> getAllSuperAdmins();

    UserDTO getSuperAdminById(String userId);

    UserDTO addSuperAdmin(UserDTO userDTO);
}
