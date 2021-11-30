package org.trishanku.oa.admin.mapper;

import org.mapstruct.Mapper;
import org.trishanku.oa.admin.entity.SBR;
import org.trishanku.oa.admin.model.SBRDTO;
import org.trishanku.oa.admin.model.SBRReturnDTO;

import java.util.List;

@Mapper
public interface SBRReturnMapper {

    SBR SBRReturnDTOToSBR(SBRReturnDTO sbrReturnDTO);
    SBRReturnDTO SBRToSBRReturnDTO(SBR sbr);
    List<SBR> SBRReturnDTOsToSBRs(List<SBRReturnDTO> sbrReturnDTOS);
    List<SBRReturnDTO> SBRsToSBRReturnDTOs(List<SBR> sbrs);

}
