package org.trishanku.oa.admin.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "ACCOUNT_TABLE",  schema = "ADMIN")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Account extends Base {

    @Id
    @Column(name = "SYSTEM_ID")
    private UUID systemId;
    @Column(name = "ID", unique = true)
    private String id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "TYPE")
    private String type;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "CURRENCY")
    private CurrencyEnum currency;
    @Column(name = "STATUS")
    private boolean status;
    @Column(name = "DELETEFLAG")
    private boolean deleteFlag;
}
