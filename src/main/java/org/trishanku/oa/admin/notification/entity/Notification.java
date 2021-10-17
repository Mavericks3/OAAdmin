package org.trishanku.oa.admin.notification.entity;

import lombok.*;
import org.trishanku.oa.admin.entity.Base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "NOTIFICATION_TABLE", schema="ADMIN")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Notification extends Base {

    @Id
    @Column(name="MESSAGE_ID")
    private UUID uuid;
    @Column(name="TO_LIST")
    private String to;
    @Column(name="CC_LIST")
    private String cc;
    @Column(name="BCC_LIST")
    private String bcc;
    @Column(name="FROM_LIST")
    private String from;
    @Column(name="SUBJECT")
    private String subject;
    @Column(name="CONTENT")
    private String content;
    @Column(name="ATTACHMENTS")
    private byte[] attachments;
    @Column(name="STATUS")
    private NotificationStatusEnum notificationStatus;
    private String type;
    @Column(name="TRANSACTION_INFORMATION")
    private String transactionInformation;
}
