package org.trishanku.oa.admin.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;

@Table(name = "RM_TABLE", schema = "ADMIN")
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
    @Column(name = "DELETE_FLAG")
    private boolean deleteFlag;
}