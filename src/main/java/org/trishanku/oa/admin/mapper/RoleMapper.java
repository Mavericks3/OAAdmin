package org.trishanku.oa.admin.mapper;

import org.mapstruct.Mapper;
import org.trishanku.oa.admin.entity.Role;
import org.trishanku.oa.admin.model.RoleDTO;

@Mapper
public interface RoleMapper {

    RoleDTO roleToRoleDTO(Role role);
    Role roleDTOToRole(RoleDTO roleDTO);
}
