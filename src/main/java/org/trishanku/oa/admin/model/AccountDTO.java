package org.trishanku.oa.admin.model;

import lombok.*;
import org.trishanku.oa.admin.entity.CurrencyEnum;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AccountDTO {

    private String accountId;
    private String name;
    private String type;
    private String description;
    private CurrencyEnum currency;
    private boolean status;

}
