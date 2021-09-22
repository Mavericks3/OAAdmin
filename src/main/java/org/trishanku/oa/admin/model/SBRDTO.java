package org.trishanku.oa.admin.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.trishanku.oa.admin.entity.*;


import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SBRDTO {

    @JsonIgnore
    private UUID uuid;
    private String sbrId;
    private Agreement agreement;
    private Customer anchorCustomer;
    private Customer counterParty;
    private DirectContactEnum directContactFlag;
    private RecourseEnum recourseFlag;
    private LimitTypeEnum limitTypeFlag;
    private CurrencyEnum appliedLimitCurrency;
    private BigDecimal appliedLimitAmount;
    private String natureOfBusiness;
    private String goodsDescription;
    private String anchorPartyAccountId;
    private String counterPartyAccountId;
    private Date transactionDate;
    private int paymentTermsDays;
    private PaymentTermsEnum paymentTermsCondition;
    private String commercialContractDetails;
    private boolean autoFinanceAvailability;
    private boolean autoFinancing;
    private String anchorCustomerContactName;
    private String anchorCustomerAddressLine1;
    private String anchorCustomerAddressLine2;
    private String anchorCustomerAddressLine3;
    private String anchorCustomerPOBox;
    private String anchorCustomerEmail;
    private String anchorCustomerFax;
    private String anchorCustomerTelephone;
    private String counterPartyContactName;
    private String counterPartyAddressLine1;
    private String counterPartyAddressLine2;
    private String counterPartyAddressLine3;
    private String counterPartyPOBox;
    private String counterPartyEmail;
    private String counterPartyFax;
    private String counterPartyTelephone;
    private CurrencyEnum managementFeeCurrency;
    private BigDecimal managementFeeAmount;
    private CurrencyEnum administrativeFeeCurrency;
    private BigDecimal administrativeFeeAmount;
    private CurrencyEnum earlyPaymentFeeCurrency;
    private BigDecimal earlyPaymentAmount;
    private float factoringCommissionRate;
    private float financingProfitMarginRate;
    private float rebateRate;
    private String rebateAccount;
    private String financingInformation;
    private CurrencyEnum invoiceServiceChargeCurrency;
    private BigDecimal invoiceServiceChargeAmount;
    private float maxLoanPercentage;
    private ProfitRateEnum profitRateType;
    private String documentsRequired;
    private String limitReference;
    private String earMarkReference;
    private Date limitExpiry;
    private CurrencyEnum limitCurrency;
    private BigDecimal limitAmount;
    private float cashMargin;
    private boolean status;
}
