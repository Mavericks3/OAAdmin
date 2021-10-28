package org.trishanku.oa.admin.notification.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.trishanku.oa.admin.notification.entity.Notification;
import org.trishanku.oa.admin.notification.entity.NotificationEvent;
import org.trishanku.oa.admin.notification.entity.NotificationStatusEnum;
import org.trishanku.oa.admin.notification.repository.NotificationRepository;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    @Autowired
     NotificationRepository notificationRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public void addMakerEvent(Object object, NotificationEvent notificationEvent) throws JsonProcessingException {

      log.info("object type is " + object.getClass().getName());
        Notification notification = Notification.builder()
                        .notificationStatus(NotificationStatusEnum.INITIATED)
                .transactionInformation(objectMapper.writeValueAsString(object))
                .type(notificationEvent.toString())
                .build();
      log.info("object as json is " + objectMapper.writeValueAsString(object));
      notificationRepository.save(notification);

    }
}
