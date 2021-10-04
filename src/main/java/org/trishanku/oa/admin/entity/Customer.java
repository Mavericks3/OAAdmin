package org.trishanku.oa.admin.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name="CUSTOMER_TABLE",  schema = "ADMIN")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
public class Customer extends Base{
    @Id
    @Column(name = "SYSTEM_ID")
    private UUID uuid;
    @Column(name = "CUSTOMER_ID")
    private String customerId;
    @Column(name = "NAME")
    private String name;
    @Column(name = "ADDRESS_LINE1")
    private String addressLine1;
    @Column(name = "ADDRESS_LINE2")
    private String addressLine2;
    @Column(name = "ADDRESS_LINE3")
    private String addressLine3;
    @Column(name = "PO_BOX")
    private String poBox;
    @Column(name = "COUNTRY")
    private String country;
    @Column(name = "EMAIL_ADDRESS")
    private String emailAddress;
    @Column(name = "VAT_REGISTRATION_NUMBER")
    private String vatRegistrationNumber;
    @Column(name = "TAX_REGISTRATION_NUMBER")
    private String taxRegistrationNumber;
    @Column(name = "DIRECTOR_NAME")
    private String directorName;
    @Column(name = "DIRECTOR_DETAILS")
    private String directorDetails;
    @Column(name = "SPONSOR_NAME")
    private String sponsorName;
    @Column(name = "SPONSOR_DETAILS")
    private String sponsorDetails;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "IS_BANK")
    private boolean bank;

}
