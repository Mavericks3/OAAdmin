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
    PortalRepository portalRepository;

    @Autowired
    JmsTemplate jmsTemplate;



    @Autowired
    ObjectMapper objectMapper;

    //@Scheduled(fixedRate = 2000000)
    public void sendMessage()
    {

        //block to send  information to portal
        if(portalRepository.findByStatus(TransmissionStatusEnum.READY_TO_BE_SENT).isPresent())
        {
            log.debug("Placing  information on the Portal queue  ==>" + JMSConfiguration.portalQueue);
            portalRepository.findByStatus(TransmissionStatusEnum.READY_TO_BE_SENT).get().stream().forEach( portal -> {


                try {
                    log.debug("Sending Notification object ==>" + objectMapper.writeValueAsString(portal));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                jmsTemplate.convertAndSend(JMSConfiguration.portalQueue,portal);
                portal.setStatus(TransmissionStatusEnum.SENT);
                portalRepository.save(portal);
            });
        }




    }



}
