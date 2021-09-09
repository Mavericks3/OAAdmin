package org.trishanku.oa.admin.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "USER_TABLE")
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
    @Column(name = "ACTIVE_STATUS")
    private boolean status;
    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
    @JoinTable(
            name = "USER_ROLES",
            joinColumns = { @JoinColumn(name = "USER_ID") },
            inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") }
    )
    private List<Role> roles;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
    @JoinTable(
            name = "USER_CUSTOMER_MAPPING",
            joinColumns = { @JoinColumn(name = "USER_ID") },
            inverseJoinColumns = { @JoinColumn(name = "CUSTOMER_ID") }
    )
    private List<Customer> customers;

    @Column(name = "EMAIL_ADDRESS")
    private String emailAddress;
}
