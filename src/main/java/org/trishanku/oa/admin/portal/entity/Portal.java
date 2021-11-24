package org.trishanku.oa.admin.portal.entity;

import lombok.*;
import org.trishanku.oa.admin.entity.TransmissionStatusEnum;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "PORTAL_TABLE", schema = "ADMIN")
public class Portal {

    @Id
    @Column(name="MESSAGE_ID")
    private UUID messsgeId;
    @Column(name="INSERT_TIME")
    private Date insertTime;
    @Column(name="PROCESSED_TIME")
    private Date processedTime;
    @Column(name="TRANSMIT_TIME")
    private Date tramsitTime;
    @Column(name="MESSAGE")
    private String message;
    @Enumerated(value = EnumType.STRING)
    @Column(name="MESSAGE_TYPE")
    private PortalMessageType messageType;
    @Column(name="TRANSMIT_RETRY_COUNT")
    private int transmitRetryCount;
    @Column(name="PROCESS_RETRY_COUNT")
    private int processRetryCount;
    @Enumerated(value = EnumType.STRING)
    @Column(name="STATUS")
    private TransmissionStatusEnum status;
}
