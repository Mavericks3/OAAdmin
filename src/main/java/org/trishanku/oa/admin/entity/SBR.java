package org.trishanku.oa.admin.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "SBR_TABLE", schema = "ADMIN")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SBR extends Base{

    @Id
    @Column(name = "SYSTEM_ID")
    private UUID uuid;
    @Column(name= "SBR_ID")
    private String sbrId;
    @ManyToOne
    @JoinColumn(name = "AGREEMENT_ID")
    private Agreement agreement;
    @ManyToOne
    @JoinColumn(name = "ANCHOR_CUSTOMER_ID")
    private Customer anchorCustomer;
    @ManyToOne
    @JoinColumn(name = "COUNTER_PARTY_ID")
    private Customer counterParty;
    @Enumerated(EnumType.STRING)
    @Column(name= "DIRECT_CONTACT_FLAG")
    private DirectContactEnum directContactFlag;
    @Enumerated(EnumType.STRING)
    @Column(name= "RECOURSE_FLAG")
    private RecourseEnum recourseFlag;
    @Enumerated(EnumType.STRING)
    @Column(name= "LIMIT_TYPE_FLAG")
    private LimitTypeEnum limitTypeFlag;
    @Enumerated(EnumType.STRING)
    @Column(name= "APPLIED_LIMIT_CURRENCY")
    private CurrencyEnum appliedLimitCurrency;
    @Column(name= "APPLIED_LIMIT_AMOUNT")
    private BigDecimal appliedLimitAmount;
    @Column(name= "NATURE_OF_BUSINESS")
    private String natureOfBusiness;
    @Column(name= "GOODS_DESCRIPTION")
    private String goodsDescription;
    @Column(name= "ANCHOR_PARTY_ACCOUNT_ID")
    private String anchorPartyAccountId;
    @Column(name= "COUNTER_PARTY_ACCOUNT_ID")
    private String counterPartyAccountId;
    @Column(name= "TRANSACTION_DATE")
    private Date transactionDate;
    @Column(name= "PAYMENT_TERMS_DAYS")
    private int paymentTermsDays;
    @Enumerated(EnumType.STRING)
    @Column(name= "PAYMENT_TERMS_CONDITION")
    private PaymentTermsEnum paymentTermsCondition;
    @Column(name= "COMMERCIAL_CONTRACT_DETAILS")
    private String commercialContractDetails;
    @Column(name= "AUTO_FINANCING_AVAILABILITY")
    private boolean autoFinanceAvailability;
    @Column(name= "AUTO_FINANCING")
    private boolean autoFinancing;
    @Column(name= "ANCHOR_CUSTOMER_CONTACT_NAME")
    private String anchorCustomerContactName;
    @Column(name= "ANCHOR_CUSTOMER_ADDRESS_LINE1")
    private String anchorCustomerAddressLine1;
    @Column(name= "ANCHOR_CUSTOMER_ADDRESS_LINE2")
    private String anchorCustomerAddressLine2;
    @Column(name= "ANCHOR_CUSTOMER_ADDRESS_LINE3")
    private String anchorCustomerAddressLine3;
    @Column(name= "ANCHOR_CUSTOMER_PO_BOX")
    private String anchorCustomerPOBox;
    @Column(name= "ANCHOR_CUSTOMER_EMAIL")
    private String anchorCustomerEmail;
    @Column(name= "ANCHOR_CUSTOMER_FAX")
    private String anchorCustomerFax;
    @Column(name= "ANCHOR_CUSTOMER_TELEPHONE")
    private String anchorCustomerTelephone;
    @Column(name= "COUNTER_PARTY_CONTACT_NAME")
    private String counterPartyContactName;
    @Column(name= "COUNTER_PARTY_ADDRESS_LINE1")
    private String counterPartyAddressLine1;
    @Column(name= "COUNTER_PARTY_ADDRESS_LINE2")
    private String counterPartyAddressLine2;
    @Column(name= "COUNTER_PARTY_ADDRESS_LINE3")
    private String counterPartyAddressLine3;
    @Column(name= "COUNTER_PARTY_PO_BOX")
    private String counterPartyPOBox;
    @Column(name= "COUNTER_PARTY_EMAIL")
    private String counterPartyEmail;
    @Column(name= "COUNTER_PARTY_FAX")
    private String counterPartyFax;
    @Column(name= "COUNTER_PARTY_TELEPHONE")
    private String counterPartyTelephone;
    @Enumerated(EnumType.STRING)
    @Column(name= "MANAGEMENT_FEE_CURRENCY")
    private CurrencyEnum managementFeeCurrency;
    @Column(name= "MANAGEMENT_FEE_AMOUNT")
    private BigDecimal managementFeeAmount;
    @Enumerated(EnumType.STRING)
    @Column(name= "ADMINISTRATIVE_FEE_CURRENCY")
    private CurrencyEnum administrativeFeeCurrency;
    @Column(name= "ADMINISTRATIVE_FEE_AMOUNT")
    private BigDecimal administrativeFeeAmount;
    @Enumerated(EnumType.STRING)
    @Column(name= "EARLY_PAYMENT_FEE_CURRENCY")
    private CurrencyEnum earlyPaymentFeeCurrency;
    @Column(name= "EARLY_PAYMENT_FEE_AMOUNT")
    private BigDecimal earlyPaymentAmount;
    @Column(name= "FACTORING_COMMISSION_RATE")
    private float factoringCommissionRate;
    @Column(name= "FINACNING_PROFIT_MARGIN_RATE")
    private float financingProfitMarginRate;
    @Column(name= "REBATE_RATE")
    private float rebateRate;
    @Column(name= "REBATE_ACCOUNT")
    private String rebateAccount;
    @Column(name= "FINANCING_INFORMATION")
    private String financingInformation;
    @Enumerated(EnumType.STRING)
    @Column(name= "INVOICE_SERVICE_CHARGE_CURRENCY")
    private CurrencyEnum invoiceServiceChargeCurrency;
    @Column(name= "INVOICE_SERVICE_CHARGE_AMOUNT")
    private BigDecimal invoiceServiceChargeAmount;
    @Column(name= "MAX_LOAN_PERCENTAGE")
    private float maxLoanPercentage;
    @Enumerated(EnumType.STRING)
    @Column(name= "PROFIT_RATE_TYPE")
    private ProfitRateEnum profitRateType;
    @Column(name= "DOCUMENTS_REQUIRED")
    private String documentsRequired;
    @Enumerated(EnumType.STRING)
    @Column(name= "LIMIT_CURRENCY")
    private CurrencyEnum limitCurrency;
    @Column(name= "LIMIT_AMOUNT")
    private BigDecimal limitAmount;
    @Column(name= "STATUS")
    private boolean status;
    @Column(name = "EXPIRY_DATE")
    private Date expiryDate;
}
