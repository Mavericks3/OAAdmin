package org.trishanku.oa.admin.notification.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.trishanku.oa.admin.notification.entity.Notification;
import org.trishanku.oa.admin.notification.entity.NotificationEvent;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface NotificationService {

    void addMakerEvent(Object object, NotificationEvent notificationEvent) throws JsonProcessingException, NoSuchAlgorithmException, InvalidKeySpecException;

    void addModificationEvent(Object object, NotificationEvent notificationEvent) throws JsonProcessingException;

    void addCheckerEvent(Object object, NotificationEvent notificationEvent) throws JsonProcessingException;

    void addDeleteEvent(Object result, NotificationEvent notificationEvent)throws JsonProcessingException;

    boolean prepareForSend(Notification notification);

    boolean updateSentStatus(Notification notification);
}
