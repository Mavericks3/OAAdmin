package org.trishanku.oa.admin.service;

import org.springframework.stereotype.Service;
import org.trishanku.oa.admin.entity.Agreement;
import org.trishanku.oa.admin.model.AgreementDTO;

import java.util.List;


public interface AgreementService {

    List<AgreementDTO> getAllAgreements();

    List<AgreementDTO> getPendingAgrements();

    AgreementDTO getAgreementByContractReferenceNumber(String contactReferenceNumber);
}
