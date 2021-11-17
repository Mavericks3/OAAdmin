package org.trishanku.oa.admin.portal.mapper;

import org.mapstruct.Mapper;
import org.trishanku.oa.admin.entity.SBR;
import org.trishanku.oa.admin.portal.entity.PortalSBR;

@Mapper
public interface PortalSBRMapper {

    PortalSBR SBRToPortalSBR(SBR sbr);
}
