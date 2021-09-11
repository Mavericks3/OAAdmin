package org.trishanku.oa.admin.mapper;

import org.mapstruct.Mapper;
import org.trishanku.oa.admin.entity.Role;
import org.trishanku.oa.admin.model.RoleDTO;

import java.util.List;

@Mapper
public interface RoleMapper {

    RoleDTO roleToRoleDTO(Role role);
    List<RoleDTO> rolesToRoleDTOs(List<Role> roles);
    Role roleDTOToRole(RoleDTO roleDTO);
    List<Role> roleDTOsToRoles(List<RoleDTO> roleDTOs);
}
