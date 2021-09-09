package org.trishanku.oa.admin.entity;

import lombok.*;

import javax.persistence.MappedSuperclass;
import java.util.Date;
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Base {

    private String createdUser;
    private String modifiedUser;
    private Date createdDate;
    private Date modifiedDate;
    private String authorisedUser;
    private Date authorisationDate;
    private TransactionStatusEnum transactionStatus;

    public void setModificationDetails(String username)
    {
        this.modifiedUser = username;
        this.modifiedDate = new Date();
        this.transactionStatus = TransactionStatusEnum.PENDING;
    }

    public void setCreationDetails(String username)
    {
        this.createdUser = username;
        this.createdDate = new Date();
        this.modifiedUser = username;
        this.modifiedDate = new Date();
        this.transactionStatus = TransactionStatusEnum.PENDING;
    }

    public void setAuthorizationDetails(String username)
    {
        this.authorisedUser = username;
        this.authorisationDate = new Date();
        this.transactionStatus = TransactionStatusEnum.MASTER;
    }
}

