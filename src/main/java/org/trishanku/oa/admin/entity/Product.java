package org.trishanku.oa.admin.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name="PRODUCT_TABLE", schema = "ADMIN")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product extends Base {

    @Id
    @Column(name = "SYSTEM_ID")
    private UUID systemId;
    @Column(name="PRODUCT_NAME")
    private String name;
    @NotEmpty
    @UniqueElements
    @Column(name = "PRODUCT_CODE")
    private String code;
    @Column(name = "EFFECTIVE_DATE")
    private Date effectiveDate;
    @Column(name = "EXPIRY_DATE")
    private Date expiryDate;
    @Column(name = "ACTIVE_STATUS")
    private boolean status;
    @Column(name = "DELETE_FLAG")
    private boolean deleteFlag;

}
