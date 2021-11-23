package org.trishanku.oa.admin.model;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProductDTO {

    //@JsonIgnore
    @JsonProperty(value = "system_id")
    private UUID systemId;
    @JsonProperty(value = "product_name")
    private String name;
    @JsonProperty(value = "product_code")
    private String code;
    private Date effectiveDate;
    private Date expiryDate;
    private boolean status;
    private boolean deleteFlag;

}
