package org.trishanku.oa.admin.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "ROLE_TABLE")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Role extends Base{

    @Id
    private UUID uuid;
    private String name;

}
