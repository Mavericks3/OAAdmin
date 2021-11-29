package org.trishanku.oa.admin.service;

import org.springframework.stereotype.Service;
import org.trishanku.oa.admin.entity.Agreement;
import org.trishanku.oa.admin.model.AgreementDTO;

import java.util.List;


public interface AgreementService {

    List<AgreementDTO> getAllAgreements();

    List<AgreementDTO> getPendingAgreements();

    AgreementDTO getAgreementByContractReferenceNumber(String contractReferenceNumber);

    AgreementDTO getAgreementByContractDocumentNumber(String contractDocumentNumber);

    AgreementDTO addAgreement(AgreementDTO agreementDTO);

    AgreementDTO modifyAgreement(String contractReferenceNumber, AgreementDTO agreementDTO);

    AgreementDTO authoriseAgreement(String contractReferenceNumber, AgreementDTO agreementDTO);

    AgreementDTO deleteAgreement(String contractReferenceNumber, AgreementDTO agreementDTO);
}
