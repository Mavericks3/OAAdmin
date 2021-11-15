package org.trishanku.oa.admin.notification.service;

import org.trishanku.oa.admin.model.EmailDTO;
import org.trishanku.oa.admin.notification.entity.Notification;

public interface TemplateUtil {

    EmailDTO getProcessedInfo(Notification notification);

}
