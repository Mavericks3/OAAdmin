package org.trishanku.oa.admin.portal.entity;

import lombok.*;
import org.trishanku.oa.admin.entity.Base;
import org.trishanku.oa.admin.entity.Customer;
import org.trishanku.oa.admin.entity.Role;
import org.trishanku.oa.admin.entity.TransmissionStatusEnum;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "PORTAL_USER_TABLE", schema = "ADMIN")

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PortalUser extends Base {


    @Column(name="MESSAGE_ID", unique = true)
    private UUID messageId;
    @Id
    @Column(name = "SYSTEM_ID")
    private UUID uuid;
    @Column(name = "USER_ID")
    private String userId;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "EFFECTIVE_DATE")
    private Date effectiveDate;
    @Column(name = "ACTIVE_STATUS")
    private boolean status;
    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
    @JoinTable(
            name = "PORTAL_USER_ROLES", schema = "ADMIN",
            joinColumns = { @JoinColumn(name = "USER_ID") },
            inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") }
    )
    private List<Role> roles;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
    @JoinTable(
            name = "PORTAL_USER_CUSTOMER_MAPPING", schema = "ADMIN",
            joinColumns = { @JoinColumn(name = "USER_ID") },
            inverseJoinColumns = { @JoinColumn(name = "CUSTOMER_ID") }
    )
    private List<PortalCustomer> customers;

    @Column(name = "EMAIL_ADDRESS", unique = true)

    private String emailAddress;
    @Enumerated(EnumType.STRING)
    @Column(name="TRANSMISSION_STATUS")
    private TransmissionStatusEnum transmissionStatus;
}
