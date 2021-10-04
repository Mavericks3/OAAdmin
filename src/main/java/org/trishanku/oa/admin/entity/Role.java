package org.trishanku.oa.admin.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "ROLE_TABLE", schema = "ADMIN")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Role extends Base{

    @Id
    @Column(name = "ROLE_ID")
    private UUID uuid;
    @Column(name="ROLE_NAME")
    private String name;

}
