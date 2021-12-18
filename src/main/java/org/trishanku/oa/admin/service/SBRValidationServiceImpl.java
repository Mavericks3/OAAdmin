package org.trishanku.oa.admin.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.trishanku.oa.admin.entity.Agreement;
import org.trishanku.oa.admin.entity.Customer;
import org.trishanku.oa.admin.entity.SBR;
import org.trishanku.oa.admin.entity.TransactionStatusEnum;
import org.trishanku.oa.admin.model.AgreementDTO;
import org.trishanku.oa.admin.model.SBRDTO;
import org.trishanku.oa.admin.repository.*;

import java.math.BigDecimal;
import java.util.*;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

@Service
@Slf4j
public class SBRValidationServiceImpl implements SBRValidationService {

    @Autowired
    SBRRepository sbrRepository;
    @Autowired
    AgreementRepository agreementRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    RMRepository rmRepository;

    @Override
    public boolean isValid(SBRDTO sbrdto) {

        if(invalidAgreement(sbrdto)) return false;
        else if(invalidAnchorCustomer(sbrdto)) return false;
        else if(invalidCounterParty(sbrdto)) return false;

        else if(sbrExists(sbrdto)) return false;
        else if(incorrectParties(sbrdto)) return  false;
        else if(invalidSbrLimit(sbrdto)) return false;


        return true;
    }

    @Override
    public boolean isValidModification(SBRDTO sbrdto) {
        if(invalidAnchorCustomer(sbrdto)) return false;
        else if(invalidCounterParty(sbrdto)) return false;


        else if(incorrectParties(sbrdto)) return false;
        else if(invalidSbrLimit(sbrdto)) return false;

        return true;
    }

    @Override
    public boolean isValidAuthorization(SBRDTO sbrdto) {
        if(agreementLimitBreach(sbrdto)) return false;
        return true;
    }

    private boolean agreementLimitBreach(SBRDTO sbrdto)
    {
        BigDecimal sbrsTotalLimitAmount = BigDecimal.ZERO;
        Agreement agreement = sbrRepository.findBySbrId(sbrdto.getSbrId()).get().getAgreement();
        sbrRepository.findByAgreementAndTransactionStatus(agreement, TransactionStatusEnum.MASTER).ifPresent(sbrList -> {
            sbrList.forEach(sbr -> {
                sbrsTotalLimitAmount.add(sbr.getLimitAmount());
            });

        });

        if((sbrsTotalLimitAmount.add(sbrRepository.findBySbrId(sbrdto.getSbrId()).get().getLimitAmount())).compareTo(agreement.getLimitAmount())>1)
            throw new RuntimeException("Agreement Limit amount breach , Agreement allocated amount " + sbrsTotalLimitAmount + " , SBR Limit amount is " + sbrdto.getLimitAmount() );
        return  false;
    }

    private boolean invalidSbrLimit(SBRDTO sbrdto)
    {
        BigDecimal agreementUnallocatedAmount = agreementRepository.findByContractReferenceNumber(sbrdto.getAgreement().getContractReferenceNumber()).get().getLimitUnallocatedAmount();
        if(sbrdto.getLimitAmount().compareTo(agreementUnallocatedAmount)>0)
            throw new RuntimeException("SBR Limit Amount " + sbrdto.getLimitAmount() + " cannot be greater than agreement unallocated amount" + agreementUnallocatedAmount);
        return false;
    }

    private boolean invalidAgreement(SBRDTO sbrdto)
    {
        Optional<Agreement> optionalAgreement = agreementRepository.findByContractReferenceNumber(sbrdto.getAgreement().getContractReferenceNumber());
        if(optionalAgreement.isEmpty())
            throw new RuntimeException("Agreement with contract reference number " + sbrdto.getAgreement().getContractReferenceNumber() + " does not exist");
        else if(!(optionalAgreement.get().isStatus()))
            throw new RuntimeException("Agreement with contract reference number " + sbrdto.getAgreement().getContractReferenceNumber() + " is not active");
        else if(optionalAgreement.get().getExpiryDate().before(sbrdto.getExpiryDate()))
            throw new RuntimeException("Agreement expiry  " + optionalAgreement.get().getExpiryDate() + " is prior to SBR expiry " + sbrdto.getExpiryDate());
        return false;
    }

    private boolean invalidAnchorCustomer(SBRDTO sbrdto)
    {
        if(customerRepository.findByCustomerId(sbrdto.getAnchorCustomer().getCustomerId()).isEmpty())
            throw new RuntimeException("Anchor customer " + sbrdto.getAnchorCustomer().getCustomerId() + " does not exist");
        return false;
    }


    private boolean invalidCounterParty(SBRDTO sbrdto)
    {
        if(customerRepository.findByCustomerId(sbrdto.getCounterParty().getCustomerId()).isEmpty())
            throw new RuntimeException("Counter party customer " + sbrdto.getCounterParty().getCustomerId() + " does not exist");
        return false;
    }


    private boolean sbrExists(SBRDTO sbrdto)
    {
        if(sbrRepository.findBySbrId(sbrdto.getSbrId()).isPresent())
            throw new RuntimeException("SBR with id " + sbrdto.getSbrId() + " already exists");
        Optional<SBR> sbr = sbrRepository.findByAgreementAndAnchorCustomerAndCounterParty(agreementRepository.findByContractReferenceNumber(sbrdto.getAgreement().getContractReferenceNumber()).get(),
            customerRepository.findByCustomerId(sbrdto.getAnchorCustomer().getCustomerId()).get(),
            customerRepository.findByCustomerId(sbrdto.getCounterParty().getCustomerId()).get());
        if(sbr.isPresent())
        throw new RuntimeException("SBR with agreement contract number " + sbrdto.getAgreement().getContractReferenceNumber() + " on anchor customer " +
                sbrdto.getAnchorCustomer().getCustomerId() + " with counter party " +
                sbrdto.getCounterParty().getCustomerId() + " already exists with SBR id " + sbr.get().getSbrId());
        return false;
    }

    private boolean incorrectParties(SBRDTO sbrdto)
    {
        Optional<Agreement> agreement = agreementRepository.findByContractReferenceNumber(sbrdto.getAgreement().getContractReferenceNumber());

        if(! agreement.get().getAnchorCustomer().getCustomerId().equalsIgnoreCase(sbrdto.getAnchorCustomer().getCustomerId()))
            throw new RuntimeException("Anchor customer " + sbrdto.getAnchorCustomer().getCustomerId() + " does not match with agreement " + sbrdto.getAgreement().getContractReferenceNumber());

        //terribly inefficient , will need to be changed later
        List<Integer> counterPartyFound= new ArrayList<Integer>();
        agreement.get().getCounterParties().forEach(customer -> {
            if(customer.getCustomerId().equalsIgnoreCase(sbrdto.getCounterParty().getCustomerId())) {
                counterPartyFound.add(1);
                return;
            }
        });

         if(!counterPartyFound.contains(1) )
            throw new RuntimeException("counter party " + sbrdto.getCounterParty().getCustomerId() + " does not match with agreement ");

        return false;
    }
}
