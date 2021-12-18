package org.trishanku.oa.admin.service;


import org.trishanku.oa.admin.model.SBRDTO;

public interface SBRValidationService {

    boolean isValid(SBRDTO sbrdto);
    boolean isValidModification(SBRDTO sbrdto);
    boolean isValidAuthorization(SBRDTO sbrdto);
}
