package org.trishanku.oa.admin.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "AGREEMENT_TABLE",  schema = "ADMIN")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Agreement extends Base{

    @Id
    @Column(name="SYSTEM_ID")
    private UUID uuid;
    @Column(name="CONTRACT_REFERENCE_NUMBER", unique = true)
    private String contractReferenceNumber;
    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product businessType;
    @ManyToOne
    @JoinColumn(name = "ANCHOR_CUSTOMER_ID")
    private Customer anchorCustomer;
    @Column(name = "CONTRACT_DOCUMENT_NUMBER", unique = true)
    private String contractDocumentNumber;
    @ManyToOne
    @JoinColumn(name = "RM_ID")
    private RM rm;
    @Column(name="REMARKS")
    private String remarks;
    @Column(name="TRANSACTION_DATE")
    private Date transactionDate;
    @Column(name = "VALID_DATE")
    private Date validDate;
    @Column(name = "EXPIRY_DATE")
    private Date expiryDate;
    @Column(name = "NUMBER_OF_COUNTER_PARTIES")
    private int numberOfCounterParties;
    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
    @JoinTable(
            name = "AGREEMENT_COUNTER_PARTIES_TABLE", schema = "ADMIN",
            joinColumns = { @JoinColumn(name = "AGREEMENT_ID") },
            inverseJoinColumns = { @JoinColumn(name = "COUNTER_PARTY_ID") }
    )
    private List<Customer> counterParties;
    @Column(name= "LIMIT_REFERENCE")
    private String limitReference;
    @Column(name= "LIMIT_EXPIRY")
    private Date limitExpiry;
    @Enumerated(EnumType.STRING)
    @Column(name= "LIMIT_CURRENCY")
    private CurrencyEnum limitCurrency;
    @Column(name= "LIMIT_AMOUNT")
    private BigDecimal limitAmount;
    @Column(name= "LIMIT_ALLOCATED_AMOUNT")
    private BigDecimal limitAllocatedAmount;
    @Column(name= "LIMIT_UNALLOCATED_AMOUNT")
    private BigDecimal limitUnallocatedAmount;
    @Column(name= "CASH_MARGIN")
    private BigDecimal cashMargin;
    @Column(name = "STATUS")
    private boolean status;
    @Enumerated(EnumType.STRING)
    @Column(name= "FINANCE_CHARGE_CURRENCY")
    private CurrencyEnum financeChargeCurrency;
    @Column(name= "FINANCE_CHARGE_AMOUNT")
    private BigDecimal financeChargeAmount;
    @Enumerated(EnumType.STRING)
    @Column(name= "SETTLEMENT_CHARGE_CURRENCY")
    private CurrencyEnum settlementChargeCurrency;
    @Column(name= "SETTLEMENT_CHARGE_AMOUNT")
    private BigDecimal settlementChargeAmount;
}
