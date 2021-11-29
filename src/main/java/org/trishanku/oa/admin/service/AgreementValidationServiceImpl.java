package org.trishanku.oa.admin.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.trishanku.oa.admin.entity.Customer;
import org.trishanku.oa.admin.entity.User;
import org.trishanku.oa.admin.model.AgreementDTO;
import org.trishanku.oa.admin.repository.AgreementRepository;
import org.trishanku.oa.admin.repository.CustomerRepository;
import org.trishanku.oa.admin.repository.ProductRepository;
import org.trishanku.oa.admin.repository.RMRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

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

    @Override
    public boolean isValid(AgreementDTO agreementDTO) {
        if(invalidAnchorCustomer(agreementDTO)) return false;
        else if(invalidRM(agreementDTO)) return false;
        else if(invalidCounterParties(agreementDTO)) return false;

        else if(agreementExists(agreementDTO)) return false;
        else if(incorrectCounterParties(agreementDTO)) return false;

        return true;
    }

    @Override
    public boolean isValidModification(AgreementDTO agreementDTO) {
        if(invalidAnchorCustomer(agreementDTO)) return false;
        else if(invalidRM(agreementDTO)) return false;
        else if(invalidCounterParties(agreementDTO)) return false;

        else if(incorrectCounterParties(agreementDTO)) return false;

        return true;
    }

    private boolean invalidAnchorCustomer(AgreementDTO agreementDTO)
    {
        if(customerRepository.findByCustomerId(agreementDTO.getAnchorCustomer().getCustomerId()).isEmpty())
            throw new RuntimeException("Anchor customer " + agreementDTO.getAnchorCustomer().getCustomerId() + " does not exist");
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
        agreementDTO.getCounterParties().forEach(customer -> {
            if (customerRepository.findByCustomerId(customer.getCustomerId()).isEmpty())
                throw new RuntimeException("Counter party customer " + customer.getCustomerId() + " does not exist");
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
