package org.trishanku.oa.admin.notification.autoprocess;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.trishanku.oa.admin.configuration.JMSConfiguration;
import org.trishanku.oa.admin.notification.entity.NotificationStatusEnum;
import org.trishanku.oa.admin.notification.repository.NotificationRepository;
import org.trishanku.oa.admin.notification.service.NotificationService;



@Component
@Slf4j
public class NotificationTask {


    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    NotificationService notificationService;

    @Autowired
    ObjectMapper objectMapper;

    //@Scheduled(fixedRate = 20000000)
    public void sendMessage()
    {
        if(notificationRepository.findByNotificationStatus(NotificationStatusEnum.INITIATED).isPresent())
        {
            notificationRepository.findByNotificationStatus(NotificationStatusEnum.INITIATED).get().stream().forEach( notification -> {
                notificationService.prepareForSend(notification);

                try {
                    log.debug("Sending Notification object ==>" + objectMapper.writeValueAsString(notification));
                    jmsTemplate.convertAndSend(JMSConfiguration.notificationQueue,notification);
                    notificationService.updateSentStatus(notification);

                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }

            });
        }


    }



}
