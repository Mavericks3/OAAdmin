package org.trishanku.oa.admin.model;

import net.minidev.json.annotate.JsonIgnore;

import org.trishanku.oa.admin.entity.Customer;
import org.trishanku.oa.admin.entity.Product;
import org.trishanku.oa.admin.entity.RM;


import java.util.Date;
import java.util.List;
import java.util.UUID;


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
}
