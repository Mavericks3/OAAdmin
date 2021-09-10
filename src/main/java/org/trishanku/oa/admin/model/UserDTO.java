package org.trishanku.oa.admin.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.trishanku.oa.admin.entity.Customer;
import org.trishanku.oa.admin.entity.Role;
import org.trishanku.oa.admin.entity.TransactionStatusEnum;


import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDTO {

    private String userId;
    private String firstName;
    private String lastName;
    private Date effectiveDate;
    private boolean status;
    private List<RoleDTO> roles;
    private List<CustomerDTO> customers;
    private String emailAddress;
    private TransactionStatusEnum transactionStatus;
}
