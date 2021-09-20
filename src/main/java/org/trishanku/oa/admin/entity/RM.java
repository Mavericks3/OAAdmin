package org.trishanku.oa.admin.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Table(name = "RM_TABLE")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RM extends Base {

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
}