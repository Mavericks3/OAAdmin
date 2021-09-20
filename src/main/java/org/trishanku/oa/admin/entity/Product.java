package org.trishanku.oa.admin.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Entity(name="PRODUCT_TABLE")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product extends Base {

    @Id
    private UUID uuid;
    private String name;
    @NotEmpty
    @UniqueElements
    private String code;

}
