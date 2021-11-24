package org.trishanku.oa.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.trishanku.oa.admin.entity.Agreement;
import org.trishanku.oa.admin.entity.Customer;
import org.trishanku.oa.admin.entity.TransactionStatusEnum;
import org.trishanku.oa.admin.mapper.AgreementMapper;
import org.trishanku.oa.admin.model.AgreementDTO;
import org.trishanku.oa.admin.repository.AgreementRepository;
import org.trishanku.oa.admin.repository.CustomerRepository;
import org.trishanku.oa.admin.repository.ProductRepository;
import org.trishanku.oa.admin.repository.RMRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AgreementServiceImpl implements AgreementService {

    @Autowired
    AgreementRepository agreementRepository;

    @Autowired
    AgreementMapper agreementMapper;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    RMRepository rmRepository;

    @Autowired
    ProductRepository productRepository;

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

    @Override
    public AgreementDTO addAgreement(AgreementDTO agreementDTO) {
        Agreement agreement = agreementMapper.AgreementDTOToAgreement(agreementDTO);
        agreement.setUuid(UUID.randomUUID());
        agreement.setBusinessType(productRepository.findByName(agreement.getBusinessType().getName()));
        agreement.setStatus(true);
        agreement.setAnchorCustomer(customerRepository.findByCustomerId(agreement.getAnchorCustomer().getCustomerId()).get());
        agreement.setRm(rmRepository.findByRmId(agreement.getRm().getRmId()).get());
        List<Customer> counterParties = new ArrayList<>();
        agreement.getCounterParties().forEach(customer -> counterParties.add(customerRepository.findByCustomerId(customer.getCustomerId()).get()));
        agreement.setCounterParties(counterParties);
        agreement.setNumberOfCounterParties(counterParties.size());
        // TO BE CHANGED AFTER RETRIEVING THE DETAILS FROM JWT
        agreement.setCreationDetails("RAVIKANTH");
        return agreementMapper.AgreementToAgreementDTO(agreementRepository.save(agreement));
    }

    @Override
    public AgreementDTO modifyAgreement(String contractReferenceNumber, AgreementDTO agreementDTO) {
        Agreement agreement = agreementRepository.findByContractReferenceNumber(contractReferenceNumber);
        if(agreement== null) throw new RuntimeException("Agreement with contract reference number " + contractReferenceNumber + " not found");
        Agreement newAgreementDetails = agreementMapper.AgreementDTOToAgreement(agreementDTO);
        newAgreementDetails.setUuid(agreement.getUuid());
        newAgreementDetails.setBusinessType(productRepository.findByName(newAgreementDetails.getBusinessType().getName()));
        newAgreementDetails.setAnchorCustomer(customerRepository.findByCustomerId(newAgreementDetails.getAnchorCustomer().getCustomerId()).get());
        newAgreementDetails.setRm(rmRepository.findByRmId(newAgreementDetails.getRm().getRmId()).get());
        List<Customer> counterParties = new ArrayList<>();
        newAgreementDetails.getCounterParties().forEach(customer -> counterParties.add(customerRepository.findByCustomerId(customer.getCustomerId()).get()));
        newAgreementDetails.setCounterParties(counterParties);
        newAgreementDetails.setNumberOfCounterParties(counterParties.size());
        // TO BE CHANGED AFTER RETRIEVING THE DETAILS FROM JWT
        agreement.setModificationDetails("RAVIKANTH");
        return agreementMapper.AgreementToAgreementDTO(agreementRepository.save(newAgreementDetails));
    }

    @Override
    public AgreementDTO authoriseAgreement(String contractReferenceNumber, AgreementDTO agreementDTO) {
        Agreement agreement = agreementRepository.findByContractReferenceNumber(contractReferenceNumber);
        if(agreement== null) throw new RuntimeException("Agreement with contract reference number " + contractReferenceNumber + " not found");
        // TO BE CHANGED AFTER RETRIEVING THE DETAILS FROM JWT
        agreement.setAuthorizationDetails("RAVIKANTH");
        return agreementMapper.AgreementToAgreementDTO(agreement);
    }

    @Override
    public AgreementDTO deleteAgreement(String contractReferenceNumber, AgreementDTO agreementDTO) {
        Agreement agreement = agreementRepository.findByContractReferenceNumber(contractReferenceNumber);
        if(agreement== null) throw new RuntimeException("Agreement with contract reference number " + contractReferenceNumber + " not found");
        agreement.setStatus(false);
        // TO BE CHANGED AFTER RETRIEVING THE DETAILS FROM JWT
        agreement.setModificationDetails("RAVIKANTH");
        return agreementMapper.AgreementToAgreementDTO(agreement);
    }
}
