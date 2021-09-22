package org.trishanku.oa.admin.service;

import org.springframework.http.ResponseEntity;
import org.trishanku.oa.admin.model.SBRDTO;

import java.util.List;

public interface SBRService {
    List<SBRDTO> getAllSBRs();

    List<SBRDTO> getAllPendingSBRs();

    List<SBRDTO> getAllMasterSBRs();
}