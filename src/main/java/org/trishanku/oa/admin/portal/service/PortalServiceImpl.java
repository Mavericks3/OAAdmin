package org.trishanku.oa.admin.portal.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.trishanku.oa.admin.entity.*;
import org.trishanku.oa.admin.model.*;
import org.trishanku.oa.admin.portal.entity.*;

import org.trishanku.oa.admin.portal.repository.*;
import org.trishanku.oa.admin.repository.*;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class PortalServiceImpl implements PortalService {



    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    RMRepository rmRepository;



    @Autowired
    UserRepository userRepository;


    @Autowired
    AgreementRepository agreementRepository;


    @Autowired
    SBRRepository sbrRepository;

    @Autowired
    PortalRepository portalRepository;
    @Autowired
    ObjectMapper objectMapper;


    @Override
    public void add(String message, PortalMessageType messageType) {
        portalRepository.save(Portal.builder().messsgeId(UUID.randomUUID()).message(message).messageType(messageType).
                insertTime(new Date()).build());

    }

    @Override
    public void addCustomer(Object result) {
        CustomerDTO customerDTO = (CustomerDTO)result;
        Optional<Customer> customer = customerRepository.findByCustomerId(customerDTO.getCustomerId());
        if (customer.isPresent())
        {
            String message = "";
            try {
                message = objectMapper.writeValueAsString(customer);

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            Portal portal = Portal.builder().messsgeId(UUID.randomUUID()).message(message).messageType(PortalMessageType.CUSTOMER).
                    insertTime(new Date()).status(TransmissionStatusEnum.READY_TO_BE_SENT).build();
            portalRepository.save(portal);


        }
    }

    @Override
    public void addRM(Object result) {
        RMDTO rmdto = (RMDTO) result;
        Optional<RM> rm = rmRepository.findByRmId(rmdto.getRmId());
        if (rm.isPresent())
        {
            String message = "";
            try {
                message = objectMapper.writeValueAsString(rm);

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            portalRepository.save(Portal.builder().messsgeId(UUID.randomUUID()).message(message).messageType(PortalMessageType.RM).
                    insertTime(new Date()).status(TransmissionStatusEnum.READY_TO_BE_SENT).build());

        }

    }

    @Override
    public void addCustomerAdmin(Object result) {
        UserDTO userDTO = (UserDTO) result;
        Optional<User> user = userRepository.findByUserId(userDTO.getUserId());
        if (user.isPresent())
        {
            String message = "";
            try {
                message = objectMapper.writeValueAsString(user);

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            portalRepository.save(Portal.builder().messsgeId(UUID.randomUUID()).message(message).messageType(PortalMessageType.USER).
                    insertTime(new Date()).status(TransmissionStatusEnum.READY_TO_BE_SENT).build());

        }
    }

    @Override
    public void addCustomerUser(Object result) {
        addCustomerAdmin(result);
    }

    @Override
    public void addBankUser(Object result) {
        addCustomerAdmin(result);
    }

    @Override
    public void addAgreement(Object result) {
        AgreementDTO agreementDTO = (AgreementDTO) result;
        Agreement agreement = agreementRepository.getById(agreementDTO.getUuid());

            String message = "";
            try {
                message = objectMapper.writeValueAsString(agreement);

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            portalRepository.save(Portal.builder().messsgeId(UUID.randomUUID()).message(message).messageType(PortalMessageType.AGREEMENT).
                    insertTime(new Date()).status(TransmissionStatusEnum.READY_TO_BE_SENT).build());


    }

    @Override
    public void addSBR(Object result) {

        SBRDTO sbrDTO = (SBRDTO) result;
        SBR sbr = sbrRepository.getById(sbrDTO.getUuid());
        String message = "";
        try {
            message = objectMapper.writeValueAsString(sbr);


        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        portalRepository.save(Portal.builder().messsgeId(UUID.randomUUID()).message(message).messageType(PortalMessageType.SBR).
                insertTime(new Date()).status(TransmissionStatusEnum.READY_TO_BE_SENT).build());
    }
}
