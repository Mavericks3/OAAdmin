package org.trishanku.oa.admin.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.trishanku.oa.admin.entity.Customer;
import org.trishanku.oa.admin.entity.SBR;
import org.trishanku.oa.admin.entity.User;
import org.trishanku.oa.admin.model.AgreementDTO;
import org.trishanku.oa.admin.repository.*;

import java.math.BigDecimal;
import java.util.*;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

@Service
@Slf4j
public class AgreementValidationServiceImpl implements AgreementValidationService {

    @Autowired
    AgreementRepository agreementRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    RMRepository rmRepository;
    @Autowired
    SBRRepository sbrRepository;

    @Override
    public boolean isValid(AgreementDTO agreementDTO) {
        if(invalidAnchorCustomer(agreementDTO)) return false;
        else if(invalidRM(agreementDTO)) return false;
        else if(invalidCounterParties(agreementDTO)) return false;

        else if(agreementExists(agreementDTO)) return false;
        else if(incorrectCounterParties(agreementDTO)) return false;
        else if(limitImbalance(agreementDTO)) return false;

        return true;
    }

    @Override
    public boolean isValidModification(AgreementDTO agreementDTO) {
        if(invalidAnchorCustomer(agreementDTO)) return false;
        else if(invalidRM(agreementDTO)) return false;
        else if(invalidCounterParties(agreementDTO)) return false;

        else if(incorrectCounterParties(agreementDTO)) return false;
        else if(limitImbalance(agreementDTO)) return false;
        else if(sbrsExpiringBefore(agreementDTO)) return false;

        return true;
    }

    private boolean sbrsExpiringBefore(AgreementDTO agreementDTO)
    {
        List<String> willBeInvalidated = new ArrayList<>();
        Optional<List<SBR>> optionalSBRList = sbrRepository.findByAgreement(agreementRepository.findByContractReferenceNumber(agreementDTO.getContractReferenceNumber()).get());
        optionalSBRList.ifPresent(sbrList -> {
            sbrList.forEach(sbr -> {
                if(sbr.getExpiryDate().before(agreementDTO.getExpiryDate())) willBeInvalidated.add(sbr.getSbrId());
            });
        });
        if(!(willBeInvalidated.isEmpty())) {
            throw new RuntimeException("SBR's " + willBeInvalidated + "expire before the Agreement expiry date");
        }
        return false;
    }
    private boolean limitImbalance(AgreementDTO agreementDTO)
    {
        if(agreementDTO.getLimitAmount().compareTo(agreementDTO.getLimitAllocatedAmount().add(agreementDTO.getLimitUnallocatedAmount()))!=0)
            throw new RuntimeException("Limit Amount is not equal to Limit allocated amount + Limit unallocated amount");
        return false;
    }

    private boolean invalidAnchorCustomer(AgreementDTO agreementDTO)
    {
        Optional<Customer> customerOptional =customerRepository.findByCustomerId(agreementDTO.getAnchorCustomer().getCustomerId());
        if(customerOptional.isEmpty())
            throw new RuntimeException("Anchor customer " + agreementDTO.getAnchorCustomer().getCustomerId() + " does not exist");
        else if(!(customerOptional.get().isStatus()))
            throw new RuntimeException("Anchor customer " + agreementDTO.getAnchorCustomer().getCustomerId() + " is not active");
        else if(customerOptional.get().getExpiryDate().before(agreementDTO.getExpiryDate()))
            throw new RuntimeException("Anchor customer expiry  " + customerOptional.get().getExpiryDate() + " is prior to agreement expiry " + agreementDTO.getExpiryDate());
        return false;
    }

    private boolean invalidRM(AgreementDTO agreementDTO)
    {
        if(rmRepository.findByRmId(agreementDTO.getRm().getRmId()).isEmpty())
            throw new RuntimeException("RM  " + agreementDTO.getRm().getRmId() + " does not exist");
        return false;
    }

    private boolean invalidCounterParties(AgreementDTO agreementDTO)
    {
        agreementDTO.getCounterParties().forEach(counterParty -> {
            Optional<Customer> customerOptional =customerRepository.findByCustomerId(counterParty.getCustomerId());
            if (customerOptional.isEmpty())
                throw new RuntimeException("Counter party customer " + counterParty.getCustomerId() + " does not exist");
            else if(!(customerOptional.get().isStatus()))
                throw new RuntimeException("Counter party customer " + counterParty.getCustomerId() + " is not active");
            else if(customerOptional.get().getExpiryDate().before(agreementDTO.getExpiryDate()))
                throw new RuntimeException("Counter party customer expiry  " + customerOptional.get().getExpiryDate() + " is prior to agreement expiry " + agreementDTO.getExpiryDate());
        });
        return false;
    }

    private boolean incorrectCounterParties(AgreementDTO agreementDTO)
    {

            //to check if there are duplicate counterparties , to be changed to list the duplicate counterparties
            List<Customer> unique = agreementDTO.getCounterParties().stream()
                    .collect(collectingAndThen(toCollection(() -> new TreeSet<>(Comparator.comparing(Customer::getCustomerId))),
                            ArrayList::new));

            if(agreementDTO.getCounterParties().size()!= unique.size())
                throw new RuntimeException("Duplicate counter parties");


        agreementDTO.getCounterParties().forEach(customerDTO -> {
            if (customerDTO.getCustomerId().equalsIgnoreCase(agreementDTO.getAnchorCustomer().getCustomerId()))
                throw new RuntimeException("Anchor party cannot be listed as counter party");
        });
        return false;
    }

    private boolean agreementExists(AgreementDTO agreementDTO)
    {
        if(agreementRepository.findByContractReferenceNumber(agreementDTO.getContractReferenceNumber()).isPresent())
            throw new RuntimeException("Agreement with contract reference number " + agreementDTO.getContractReferenceNumber() + " already exists");
        if(agreementRepository.findByContractDocumentNumber(agreementDTO.getContractDocumentNumber()).isPresent())
            throw new RuntimeException("Agreement with contract document number " + agreementDTO.getContractDocumentNumber() + " already exists");
        if(agreementRepository.findByAnchorCustomerAndBusinessType(customerRepository.findByCustomerId(agreementDTO.getAnchorCustomer().getCustomerId()).get(), productRepository.findByName(agreementDTO.getBusinessType().getName())).isPresent())
            throw new RuntimeException("Agreement for customer " + agreementDTO.getAnchorCustomer().getCustomerId() + " on " + agreementDTO.getBusinessType().getName() + " already exists");
        return false;
    }
}
