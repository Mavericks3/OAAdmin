package org.trishanku.oa.admin.portal.autoprocess;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.trishanku.oa.admin.configuration.JMSConfiguration;
import org.trishanku.oa.admin.entity.TransmissionStatusEnum;
import org.trishanku.oa.admin.notification.entity.NotificationStatusEnum;
import org.trishanku.oa.admin.notification.repository.NotificationRepository;
import org.trishanku.oa.admin.notification.service.NotificationService;
import org.trishanku.oa.admin.portal.repository.*;

@Component
@Slf4j
public class PortalTask {



    @Autowired
    PortalSBRRepository portalSBRRepository;
    @Autowired
    PortalAgreementRepository portalAgreementRepository;
    @Autowired
    PortalRMRepository portalRMRepository;
    @Autowired
    PortalUserRepository portalUserRepository;
    @Autowired
    PortalCustomerRepository portalCustomerRepository;

    @Autowired
    JmsTemplate jmsTemplate;



    @Autowired
    ObjectMapper objectMapper;

    @Scheduled(fixedRate = 2000000000)
    public void sendMessage()
    {

        //block to send customer information to portal
        if(portalCustomerRepository.findByTransmissionStatus(TransmissionStatusEnum.READY_TO_BE_SENT).isPresent())
        {
            log.debug("Placing Customers information on the Portal queue  ==>" + JMSConfiguration.portalQueue);
            portalCustomerRepository.findByTransmissionStatus(TransmissionStatusEnum.READY_TO_BE_SENT).get().stream().forEach( portalCustomer -> {


                try {
                    log.debug("Sending Notification object ==>" + objectMapper.writeValueAsString(portalCustomer));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                jmsTemplate.convertAndSend(JMSConfiguration.portalQueue,portalCustomer);
                portalCustomer.setTransmissionStatus(TransmissionStatusEnum.SENT);
                portalCustomerRepository.save(portalCustomer);
            });
        }

        //block to send user (Customer Admin and Customer User) information to portal
        if(portalUserRepository.findByTransmissionStatus(TransmissionStatusEnum.READY_TO_BE_SENT).isPresent())
        {
            log.debug("Placing Users information on the Portal queue  ==>" + JMSConfiguration.portalQueue);
            portalUserRepository.findByTransmissionStatus(TransmissionStatusEnum.READY_TO_BE_SENT).get().stream().forEach( portalUser -> {


                try {
                    log.debug("Sending Notification object ==>" + objectMapper.writeValueAsString(portalUser));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                jmsTemplate.convertAndSend(JMSConfiguration.portalQueue,portalUser);
                portalUser.setTransmissionStatus(TransmissionStatusEnum.SENT);
                portalUserRepository.save(portalUser);
            });
        }

        //block to send RM  information to portal
        if(portalRMRepository.findByTransmissionStatus(TransmissionStatusEnum.READY_TO_BE_SENT).isPresent())
        {
            log.debug("Placing RMs information on the Portal queue  ==>" + JMSConfiguration.portalQueue);
            portalRMRepository.findByTransmissionStatus(TransmissionStatusEnum.READY_TO_BE_SENT).get().stream().forEach( portalRM -> {


                try {
                    log.debug("Sending Notification object ==>" + objectMapper.writeValueAsString(portalRM));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                jmsTemplate.convertAndSend(JMSConfiguration.portalQueue,portalRM);
                portalRM.setTransmissionStatus(TransmissionStatusEnum.SENT);
                portalRMRepository.save(portalRM);
            });
        }

        //block to send Agreement  information to portal
        if(portalAgreementRepository.findByTransmissionStatus(TransmissionStatusEnum.READY_TO_BE_SENT).isPresent())
        {
            log.debug("Placing Agreements information on the Portal queue  ==>" + JMSConfiguration.portalQueue);
            portalAgreementRepository.findByTransmissionStatus(TransmissionStatusEnum.READY_TO_BE_SENT).get().stream().forEach( portalAgreement -> {


                try {
                    log.debug("Sending Notification object ==>" + objectMapper.writeValueAsString(portalAgreement));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                jmsTemplate.convertAndSend(JMSConfiguration.portalQueue,portalAgreement);
                portalAgreement.setTransmissionStatus(TransmissionStatusEnum.SENT);
                portalAgreementRepository.save(portalAgreement);
            });
        }

        //block to send SBR  information to portal
        if(portalSBRRepository.findByTransmissionStatus(TransmissionStatusEnum.READY_TO_BE_SENT).isPresent())
        {
            log.debug("Placing SBR's information on the Portal queue  ==>" + JMSConfiguration.portalQueue);
            portalSBRRepository.findByTransmissionStatus(TransmissionStatusEnum.READY_TO_BE_SENT).get().stream().forEach( portalSBR -> {


                try {
                    log.debug("Sending Notification object ==>" + objectMapper.writeValueAsString(portalSBR));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                jmsTemplate.convertAndSend(JMSConfiguration.portalQueue,portalSBR);
                portalSBR.setTransmissionStatus(TransmissionStatusEnum.SENT);
                portalSBRRepository.save(portalSBR);
            });
        }


    }



}
