package org.trishanku.oa.admin.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.trishanku.oa.admin.entity.Agreement;
import org.trishanku.oa.admin.entity.Customer;
import org.trishanku.oa.admin.entity.TransactionStatusEnum;
import org.trishanku.oa.admin.jwtauthentication.configuration.service.JWTUtil;
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
@Slf4j
public class AgreementServiceImpl implements AgreementService {

    @Autowired
    AgreementRepository agreementRepository;
    @Autowired
    AgreementValidationService agreementValidationService;
    @Autowired
    JWTUtil jwtUtil;

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
    public List<AgreementDTO> getPendingAgreements() {
        return agreementMapper.AgreementsToAgreementDTOs(agreementRepository.findByTransactionStatus(TransactionStatusEnum.PENDING).get());
    }

    @Override
    public AgreementDTO getAgreementByContractReferenceNumber(String contractReferenceNumber) {
        return agreementMapper.AgreementToAgreementDTO(agreementRepository.findByContractReferenceNumber(contractReferenceNumber).get());
    }

    @Override
    public AgreementDTO getAgreementByContractDocumentNumber(String contractDocumentNumber) {
        return agreementMapper.AgreementToAgreementDTO(agreementRepository.findByContractDocumentNumber(contractDocumentNumber).get());
    }

    @Override
    public AgreementDTO addAgreement(AgreementDTO agreementDTO) {

        if(!agreementValidationService.isValid(agreementDTO)) throw new RuntimeException(" Validation failed on the request");
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
        agreement.setCreationDetails(jwtUtil.extractUsernameFromRequest());
        return agreementMapper.AgreementToAgreementDTO(agreementRepository.save(agreement));
    }

    @Override
    public AgreementDTO modifyAgreement(String contractReferenceNumber, AgreementDTO agreementDTO) {
        Agreement agreement = null;
        if(agreementRepository.findByContractReferenceNumber(contractReferenceNumber).isEmpty())
            throw new RuntimeException("Agreement with contract reference number " + contractReferenceNumber + " not found");
        else agreement = agreementRepository.findByContractReferenceNumber(contractReferenceNumber).get();
        if(!agreementValidationService.isValidModification(agreementDTO)) throw new RuntimeException(" Validation failed on the request");
        Agreement newAgreementDetails = agreementMapper.AgreementDTOToAgreement(agreementDTO);
        newAgreementDetails.setUuid(agreement.getUuid());
        newAgreementDetails.setBusinessType(productRepository.findByName(newAgreementDetails.getBusinessType().getName()));
        newAgreementDetails.setAnchorCustomer(customerRepository.findByCustomerId(newAgreementDetails.getAnchorCustomer().getCustomerId()).get());
        newAgreementDetails.setRm(rmRepository.findByRmId(newAgreementDetails.getRm().getRmId()).get());
        List<Customer> counterParties = new ArrayList<>();
        newAgreementDetails.getCounterParties().forEach(customer -> counterParties.add(customerRepository.findByCustomerId(customer.getCustomerId()).get()));
        newAgreementDetails.setCounterParties(counterParties);
        newAgreementDetails.setNumberOfCounterParties(counterParties.size());
        newAgreementDetails.setCreatedUser(agreement.getCreatedUser());
        newAgreementDetails.setCreatedDate(agreement.getCreatedDate());
        newAgreementDetails.setModificationDetails(jwtUtil.extractUsernameFromRequest());
        return agreementMapper.AgreementToAgreementDTO(agreementRepository.save(newAgreementDetails));
    }

    @Override
    public AgreementDTO authoriseAgreement(String contractReferenceNumber, AgreementDTO agreementDTO) {
        Agreement agreement = agreementRepository.findByContractReferenceNumber(contractReferenceNumber).get();
        if(agreement== null) throw new RuntimeException("Agreement with contract reference number " + contractReferenceNumber + " not found");
        agreement.setAuthorizationDetails(jwtUtil.extractUsernameFromRequest());
        agreementRepository.save(agreement);
        return agreementMapper.AgreementToAgreementDTO(agreement);
    }

    @Override
    public AgreementDTO deleteAgreement(String contractReferenceNumber, AgreementDTO agreementDTO) {
        Agreement agreement = agreementRepository.findByContractReferenceNumber(contractReferenceNumber).get();
        if(agreement== null) throw new RuntimeException("Agreement with contract reference number " + contractReferenceNumber + " not found");
        agreement.setStatus(false);
        agreement.setModificationDetails(jwtUtil.extractUsernameFromRequest());
        agreementRepository.save(agreement);
        return agreementMapper.AgreementToAgreementDTO(agreement);
    }
}
