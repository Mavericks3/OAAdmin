package org.trishanku.oa.admin.service;

import org.trishanku.oa.admin.model.AgreementDTO;

public interface AgreementValidationService {

    boolean isValid(AgreementDTO agreementDTO);
    boolean isValidModification(AgreementDTO agreementDTO);
}
