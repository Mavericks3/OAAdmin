package org.trishanku.oa.admin.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
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
    @Column(name="FIRST_NAME")
    private String firstName;
    @Column(name="LAST_NAME")
    private String lastName;
    @Column(name = "EMAIL_ADDRESS")
    private String emailAddress;
    @Column(name = "JOINING_DATE")
    private Date joiningDate;
    @Column(name = "EFFECTIVE_DATE")
    private Date effectiveDate;
    @Column(name = "EXPIRY_DATE")
    private Date expiryDate;
    @Column(name = "ACTIVE_STATUS")
    private boolean status;
    @Column(name = "DELETE_FLAG")
    private boolean deleteFlag;
    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH })
    @JoinTable(
            name = "RM_CUSTOMERS_TABLE", schema = "ADMIN",
            joinColumns = { @JoinColumn(name = "RM_ID") },
            inverseJoinColumns = { @JoinColumn(name = "CUSTOMER_ID") }
    )
    private List<Customer> customers;
}