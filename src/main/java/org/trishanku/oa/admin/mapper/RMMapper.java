package org.trishanku.oa.admin.mapper;

import org.mapstruct.Mapper;
import org.trishanku.oa.admin.entity.RM;
import org.trishanku.oa.admin.model.RMDTO;

import java.util.List;

@Mapper
public interface RMMapper {

    RM RMDTOToRM(RMDTO rmdto);
    RMDTO RMToRMDTO(RM rm);
    List<RM> RMDTOsToRMs(List<RMDTO> rmdtoList);
    List<RMDTO> RMsToRMDTOs(List<RM> rmList);
}
