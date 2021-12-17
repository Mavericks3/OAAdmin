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
    private CurrencyEnum financeChargeCurrency;
    private BigDecimal financeChargeAmount;
    private CurrencyEnum settlementChargeCurrency;
    private BigDecimal settlementChargeAmount;
    private BigDecimal limitAllocatedAmount;
    private BigDecimal limitUnallocatedAmount;
}
