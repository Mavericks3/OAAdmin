package org.trishanku.oa.admin.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "USER_TABLE", schema = "ADMIN")

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User extends Base{

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
    @Column(name = "EXPIRY_DATE")
    private Date expiryDate;
    @Column(name = "ACTIVE_STATUS")
    private boolean status;
    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH })
    @JoinTable(
            name = "USER_ROLES", schema = "ADMIN",
            joinColumns = { @JoinColumn(name = "USER_ID") },
            inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") }
    )
    private List<Role> roles;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH })
    @JoinTable(
            name = "USER_CUSTOMER_MAPPING", schema = "ADMIN",
            joinColumns = { @JoinColumn(name = "USER_ID") },
            inverseJoinColumns = { @JoinColumn(name = "CUSTOMER_ID") }
    )
    private List<Customer> customers;

    @Column(name = "EMAIL_ADDRESS", unique = true)

    private String emailAddress;
    @Column(name = "DELETE_FLAG")
    private boolean deleteFlag;
}
