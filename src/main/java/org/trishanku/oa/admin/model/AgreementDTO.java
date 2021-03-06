package org.trishanku.oa.admin.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import org.trishanku.oa.admin.entity.*;


import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AgreementDTO {

    @JsonIgnore
    private UUID uuid;
    private String contractReferenceNumber;
    private Product businessType;
    private Customer anchorCustomer;
    private String contractDocumentNumber;
    private RM rm;
    private String remarks;
    private Date transactionDate;
    private Date validDate;
    private Date expiryDate;
    private int numberOfCounterParties;
    private List<Customer> counterParties;
    private String limitReference;
    private Date limitExpiry;
    private CurrencyEnum limitCurrency;
    private BigDecimal limitAmount;
    private float cashMargin;
    private boolean status;
    private TransactionStatusEnum transactionStatus;
    private BigDecimal limitAllocatedAmount;
    private BigDecimal limitUnallocatedAmount;
    private CurrencyEnum cableChargeCurrency;
    private BigDecimal cableChargeAmount;
    private CurrencyEnum communicationChargeCurrency;
    private BigDecimal communicationChargeAmount;
    private boolean anchorPartyApprovalRequired;
    private boolean counterPartyApprovalRequired;
    private boolean autoFinance;
    private boolean autoSettlement;
    private CurrencyEnum financeServiceChargeCurrency;
    private BigDecimal financeServiceChargeAmount;
    private CurrencyEnum settlementServiceChargeCurrency;
    private BigDecimal settlementServiceChargeAmount;
    private CurrencyEnum invoiceServiceChargeCurrency;
    private BigDecimal invoiceServiceChargeAmount;
}
