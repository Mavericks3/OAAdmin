package org.trishanku.oa.admin.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;

@Table(name = "RM_TABLE")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RM extends Base {

    @Id
    private UUID uuid;
    @UniqueElements
    private String rmId;
    private String name;
    private String emailAddress;
    private Date joiningDate;
    private Date validDate;
    private Date expiryDate;
}