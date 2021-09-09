package org.trishanku.oa.admin.entity;

import javax.persistence.MappedSuperclass;
import java.util.Date;
@MappedSuperclass
public class Base {

    private String createdUser;
    private String modifiedUser;
    private Date createdDate;
    private Date modifiedDate;
    private transactionStatusEnum transactionStatus;
}
