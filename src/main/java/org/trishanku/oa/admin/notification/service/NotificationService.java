package org.trishanku.oa.admin.notification.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.trishanku.oa.admin.notification.entity.NotificationEvent;

public interface NotificationService {

    void addMakerEvent(Object object, NotificationEvent notificationEvent) throws JsonProcessingException;
}
