package org.trishanku.oa.admin.notification.service;

import org.trishanku.oa.admin.notification.entity.NotificationEvent;

public interface NotificationUtil {

    String getEventDefaultToAddresses(NotificationEvent notificationEvent);
    String getEventDefaultCcAddresses(NotificationEvent notificationEvent);
    String getEventDefaultBccAddresses(NotificationEvent notificationEvent);
}
