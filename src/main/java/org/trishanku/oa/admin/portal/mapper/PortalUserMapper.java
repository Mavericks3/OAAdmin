package org.trishanku.oa.admin.portal.mapper;

import org.mapstruct.Mapper;
import org.trishanku.oa.admin.entity.User;
import org.trishanku.oa.admin.portal.entity.PortalUser;

@Mapper
public interface PortalUserMapper {

    PortalUser UserToPortalUser(User user);
}
