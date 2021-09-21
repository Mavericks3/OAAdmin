package org.trishanku.oa.admin.mapper;

import org.mapstruct.Mapper;
import org.trishanku.oa.admin.entity.SBR;
import org.trishanku.oa.admin.model.SBRDTO;

import java.util.List;

@Mapper
public interface SBRMapper {

    SBR SBRDTOToSBR(SBRDTO sbrdto);
    SBRDTO SBRToSBRDTO(SBR sbr);
    List<SBR> SBRDTOsToSBRs(List<SBRDTO> sbrdtos);
    List<SBRDTO> SBRsToSBRDTOs(List<SBR> sbrs);

}
