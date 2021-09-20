package org.trishanku.oa.admin.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Agreement extends Base{

    @Id
    private UUID uuid;
    @UniqueElements
    private String contractReferenceNumber;
    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product businessType;
    @ManyToOne
    @JoinColumn(name = "SYSTEM_ID")
    private Customer anchorCustomer;
    @UniqueElements
    private String contractDocumentNumber;
    @ManyToOne
    @JoinColumn(name = "RM_ID")
    private RM rm;
    private String remarks;
    private Date transactionDate;
    private Date validDate;
    private Date expiryDate;
    private int numberOfCounterParties;
    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
    @JoinTable(
            name = "AGREEMENT_COUNTER_PARTIES",
            joinColumns = { @JoinColumn(name = "AGREEMENT_ID") },
            inverseJoinColumns = { @JoinColumn(name = "COUNTER_PARTY_ID") }
    )

    private List<Customer> counterParties;


}
