package org.trishanku.oa.admin.service;


import org.trishanku.oa.admin.model.SBRDTO;
import org.trishanku.oa.admin.model.SBRReturnDTO;

import java.util.List;

public interface SBRService {
    List<SBRReturnDTO> getAllSBRs();

    List<SBRReturnDTO> getAllPendingSBRs();

    List<SBRReturnDTO> getAllMasterSBRs();

    SBRReturnDTO addSBR(SBRDTO sbrdto);

    SBRReturnDTO authoriseSBR(SBRDTO sbrdto);

    SBRReturnDTO getSBRById(String sbrId);

    SBRReturnDTO deleteSBR(SBRDTO sbrdto);

    List<SBRReturnDTO> getSBRsByAnchorCustomer(String anchorCustomerId);

    List<SBRReturnDTO> getSBRsByCounterParty(String counterPartyId);

    SBRReturnDTO editSBR(SBRDTO sbrdto);
}
