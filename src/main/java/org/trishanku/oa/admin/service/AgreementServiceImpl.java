package org.trishanku.oa.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.trishanku.oa.admin.entity.Agreement;
import org.trishanku.oa.admin.entity.TransactionStatusEnum;
import org.trishanku.oa.admin.mapper.AgreementMapper;
import org.trishanku.oa.admin.model.AgreementDTO;
import org.trishanku.oa.admin.repository.AgreementRepository;

import java.util.List;

@Service
public class AgreementServiceImpl implements AgreementService {

    @Autowired
    AgreementRepository agreementRepository;

    @Autowired
    AgreementMapper agreementMapper;

    @Override
    public List<AgreementDTO> getAllAgreements() {
        return agreementMapper.AgreementsToAgreementDTOs(agreementRepository.findAll());
    }

    @Override
    public List<AgreementDTO> getPendingAgrements() {
        return agreementMapper.AgreementsToAgreementDTOs(agreementRepository.findByTransactionStatus(TransactionStatusEnum.PENDING));
    }

    @Override
    public AgreementDTO getAgreementByContractReferenceNumber(String contractReferenceNumber) {
        return agreementMapper.AgreementToAgreementDTO(agreementRepository.findByContractReferenceNumber(contractReferenceNumber));
    }

    @Override
    public AgreementDTO getAgreementByContractDocumentNumber(String contractDocumentNumber) {
        return agreementMapper.AgreementToAgreementDTO(agreementRepository.findByContractDocumentNumber(contractDocumentNumber));
    }
}
