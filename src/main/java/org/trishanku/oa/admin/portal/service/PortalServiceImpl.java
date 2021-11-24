package org.trishanku.oa.admin.portal.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.trishanku.oa.admin.entity.*;
import org.trishanku.oa.admin.model.*;
import org.trishanku.oa.admin.portal.entity.*;
import org.trishanku.oa.admin.portal.mapper.*;
import org.trishanku.oa.admin.portal.repository.*;
import org.trishanku.oa.admin.repository.*;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class PortalServiceImpl implements PortalService {

    @Autowired
    PortalCustomerRepository portalCustomerRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    RMRepository rmRepository;

    @Autowired
    PortalCustomerMapper portalCustomerMapper;

    @Autowired
    PortalRMMapper portalRMMapper;

    @Autowired
    PortalRMRepository portalRMRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PortalUserMapper portalUserMapper;

    @Autowired
    PortalUserRepository portalUserRepository;

    @Autowired
    PortalAgreementMapper portalAgreementMapper;
    @Autowired
    PortalAgreementRepository portalAgreementRepository;
    @Autowired
    AgreementRepository agreementRepository;

    @Autowired
    PortalSBRMapper portalSBRMapper;
    @Autowired
    PortalSBRRepository portalSBRRepository;
    @Autowired
    SBRRepository sbrRepository;

    @Override
    public void addCustomer(Object result) {
        CustomerDTO customerDTO = (CustomerDTO)result;
        Optional<Customer> customer = customerRepository.findById(customerDTO.getUuid());
        if (customer.isPresent())
        {
            PortalCustomer portalCustomer = portalCustomerMapper.CustomerToPortalCustomer(customer.get());
            portalCustomer.setMessageId(UUID.randomUUID());
            portalCustomer.setTransmissionStatus(TransmissionStatusEnum.READY_TO_BE_SENT);
            portalCustomerRepository.save(portalCustomer);
        }
    }

    @Override
    public void addRM(Object result) {
        RMDTO rmdto = (RMDTO) result;
        Optional<RM> rm = rmRepository.findByRmId(rmdto.getRmId());
        PortalRM portalRM = portalRMMapper.rmToPortalRM(rm.get());
        portalRM.setMessageId(UUID.randomUUID());
        portalRM.setTransmissionStatus(TransmissionStatusEnum.READY_TO_BE_SENT);
        portalRMRepository.save(portalRM);

    }

    @Override
    public void addCustomerAdmin(Object result) {
        UserDTO userDTO = (UserDTO) result;
        Optional<User> user = userRepository.findByUserId(userDTO.getUserId());
        if(user.isPresent())
        {
            PortalUser portalUser = portalUserMapper.UserToPortalUser(user.get());
            portalUser.setMessageId(UUID.randomUUID());
            portalUser.setTransmissionStatus(TransmissionStatusEnum.READY_TO_BE_SENT);
            portalUserRepository.save(portalUser);
        }
    }

    @Override
    public void addCustomerUser(Object result) {
        addCustomerAdmin(result);
    }

    @Override
    public void addAgreement(Object result) {
        AgreementDTO agreementDTO = (AgreementDTO) result;
        Agreement agreement = agreementRepository.getById(agreementDTO.getUuid());
        PortalAgreement portalAgreement = portalAgreementMapper.AgreementToPortalAgreement(agreement);
        portalAgreement.setMessageId(UUID.randomUUID());
        portalAgreement.setTransmissionStatus(TransmissionStatusEnum.READY_TO_BE_SENT);
        portalAgreementRepository.save(portalAgreement);
    }

    @Override
    public void addSBR(Object result) {
        SBRDTO sbrdto = (SBRDTO) result;
        SBR sbr = sbrRepository.getById(sbrdto.getUuid());
        PortalSBR portalSBR = portalSBRMapper.SBRToPortalSBR(sbr);
        portalSBR.setMessageId(UUID.randomUUID());
        portalSBR.setTransmissionStatus(TransmissionStatusEnum.READY_TO_BE_SENT);
        portalSBRRepository.save(portalSBR);
    }
}
