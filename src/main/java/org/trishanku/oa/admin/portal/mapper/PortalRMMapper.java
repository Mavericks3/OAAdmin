package org.trishanku.oa.admin.portal.mapper;

import org.mapstruct.Mapper;
import org.trishanku.oa.admin.entity.RM;
import org.trishanku.oa.admin.portal.entity.PortalRM;

@Mapper
public interface PortalRMMapper {

    PortalRM rmToPortalRM(RM rm);
}
