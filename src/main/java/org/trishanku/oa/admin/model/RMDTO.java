package org.trishanku.oa.admin.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.trishanku.oa.admin.entity.Customer;
import org.trishanku.oa.admin.entity.TransactionStatusEnum;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RMDTO {


    private String rmId;
    // @Size(min = 2, groups = "BasicInfo.class" ,message = "First name should be of minimum 2 characters")
    @Size(min = 2, message = "First name should be of minimum 2 characters")
    private String firstName;
    // @Size(min = 2, groups = "BasicInfo.class" ,message = "First name should be of minimum 2 characters")
    @Size(min = 2, message = "Last name should be of minimum 2 characters")
    private String lastName;
    private String emailAddress;
    private Date joiningDate;
    @FutureOrPresent(message = "Effective date should be either present or future date")
    private Date effectiveDate;
    private Date expiryDate;
    private boolean status;
    private boolean deleteFlag;
    private TransactionStatusEnum transactionStatus;
    private List<Customer> customers;
}
