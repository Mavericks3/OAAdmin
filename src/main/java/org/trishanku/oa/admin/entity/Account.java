package org.trishanku.oa.admin.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "ACCOUNT_TABLE",  schema = "ADMIN")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Account extends Base {

    @Id
    @Column(name = "SYSTEM_ID")
    private UUID systemId;
    @Column(name = "ID", unique = true)
    private String accountId;
    @Column(name = "NAME")
    private String name;
    @Column(name = "TYPE")
    private String type;
    @Column(name = "DESCRIPTION")
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(name = "CURRENCY")
    private CurrencyEnum currency;
    @Column(name = "STATUS")
    private boolean status;
    @Column(name = "DELETE_FLAG")
    private boolean deleteFlag;
    @Enumerated(EnumType.STRING)
    @Column(name = "DEBIT_CREDIT_FLAG")
    private DebitCreditFlagEnum debitCreditFlag;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;
}
