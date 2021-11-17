package org.trishanku.oa.admin.portal.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.trishanku.oa.admin.entity.Base;
import org.trishanku.oa.admin.entity.TransmissionStatusEnum;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Table(name = "PORTAL_RM_TABLE", schema = "ADMIN")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PortalRM extends Base {


    @Column(name="MESSAGE_ID", unique = true)
    private UUID messageId;
    @Id
    @Column(name = "SYSTEM_ID")
    private UUID uuid;

    @Column(name="RM_ID", unique = true)
    private String rmId;
    @Column(name="RM_NAME")
    private String name;
    @Column(name = "EMAIL_ADDRESS")
    private String emailAddress;
    @Column(name = "JOINING_DATE")
    private Date joiningDate;
    @Column(name = "VALID_DATE")
    private Date validDate;
    @Column(name = "EXPIRY_DATE")
    private Date expiryDate;
    @Column(name = "ACTIVE_STATUS")
    private boolean status;
    @Enumerated(EnumType.STRING)
    @Column(name="TRANSMISSION_STATUS")
    private TransmissionStatusEnum transmissionStatus;
}