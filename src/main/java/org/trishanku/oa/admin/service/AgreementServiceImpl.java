package org.trishanku.oa.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
}
