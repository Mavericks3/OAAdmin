package org.trishanku.oa.admin.notification.autoprocess;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.trishanku.oa.admin.configuration.JMSConfiguration;
import org.trishanku.oa.admin.notification.entity.Notification;
import org.trishanku.oa.admin.notification.entity.NotificationStatusEnum;
import org.trishanku.oa.admin.notification.repository.NotificationRepository;
import org.trishanku.oa.admin.notification.service.NotificationService;

import javax.jms.JMSException;
import javax.jms.Message;

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

    @Scheduled(fixedRate = 20000)
    public void sendMessage()
    {
        if(notificationRepository.findByNotificationStatus(NotificationStatusEnum.INITIATED).isPresent())
        {
            notificationRepository.findByNotificationStatus(NotificationStatusEnum.INITIATED).get().stream().forEach( notification -> {
                notificationService.prepareForSend(notification);

                try {
                    log.debug("Sending Notification object ==>" + objectMapper.writeValueAsString(notification));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                jmsTemplate.convertAndSend(JMSConfiguration.notificationQueue,notification);
            });
        }


    }



}
