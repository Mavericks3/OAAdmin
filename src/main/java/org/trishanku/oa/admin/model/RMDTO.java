package org.trishanku.oa.admin.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RMDTO {


    private String rmId;
    private String name;
    private String emailAddress;
    private Date joiningDate;
    private Date validDate;
    private Date expiryDate;
    private boolean status;

}
