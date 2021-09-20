package org.trishanku.oa.admin.service;

import org.trishanku.oa.admin.exception.ResourceAlreadyExistsException;
import org.trishanku.oa.admin.model.UserDTO;

import java.util.List;

public interface SuperAdminService {


    List<UserDTO> getAllSuperAdmins();

    UserDTO getSuperAdminById(String userId);

    UserDTO addSuperAdmin(UserDTO userDTO) throws ResourceAlreadyExistsException;

    UserDTO modifySuperAdmin(String userId, UserDTO userDTO);

    UserDTO authoriseSuperAdmin(String userId);

    List<UserDTO> getPendingSuperAdmins();

    UserDTO deleteSuperAdmin(String userId);
}
