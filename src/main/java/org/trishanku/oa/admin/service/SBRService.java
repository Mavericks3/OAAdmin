package org.trishanku.oa.admin.service;


import org.trishanku.oa.admin.model.SBRDTO;
import org.trishanku.oa.admin.model.SBRReturnDTO;

import java.util.List;

public interface SBRService {
    List<SBRDTO> getAllSBRs();

    List<SBRDTO> getAllPendingSBRs();

    List<SBRDTO> getAllMasterSBRs();

    SBRDTO addSBR(SBRDTO sbrdto);

    SBRDTO authoriseSBR(SBRDTO sbrdto);

    SBRDTO getSBRById(String sbrId);

    SBRDTO deleteSBR(SBRDTO sbrdto);

    List<SBRDTO> getSBRsByAnchorCustomer(String anchorCustomerId);

    List<SBRDTO> getSBRsByCounterParty(String counterPartyId);

    SBRDTO editSBR(SBRDTO sbrdto);

    String getNewReference();
}
