package org.trishanku.oa.admin.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.trishanku.oa.admin.entity.TransactionStatusEnum;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CustomerDTO {
    @JsonIgnore
    private UUID uuid;
    private String customerId;
    private String name;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String poBox;
    private String country;
    private String emailAddress;
    private String vatRegistrationNumber;
    private String taxRegistrationNumber;
    private String directorName;
    private String directorDetails;
    private String sponsorName;
    private String sponsorDetails;
    private boolean status;
    private boolean bank;
    private boolean nonCustomer;
    private Date effectiveDate;
    private Date expiryDate;
    private boolean deleteFlag;
    private TransactionStatusEnum transactionStatus;

}
