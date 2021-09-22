package org.trishanku.oa.admin.service;


import org.trishanku.oa.admin.model.SBRDTO;

import java.util.List;

public interface SBRService {
    List<SBRDTO> getAllSBRs();

    List<SBRDTO> getAllPendingSBRs();

    List<SBRDTO> getAllMasterSBRs();

    SBRDTO save(SBRDTO sbrdto);

    SBRDTO authorise(SBRDTO sbrdto);

    SBRDTO getSBRById(String sbrId);

    SBRDTO delete(SBRDTO sbrdto);

    List<SBRDTO> getSBRsByAnchorCustomer(String anchorCustomerId);

    List<SBRDTO> getSBRsByCounterParty(String counterPartyId);

    SBRDTO edit(SBRDTO sbrdto);
}
