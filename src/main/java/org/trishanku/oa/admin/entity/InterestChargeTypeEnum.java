package org.trishanku.oa.admin.entity;

import java.util.HashMap;
import java.util.Map;

public enum InterestChargeTypeEnum {

    UPFRONT,SETTLEMENT,INVALID_CATALOG_STATUS;

    private static Map<String, InterestChargeTypeEnum> valueMap;
    public static InterestChargeTypeEnum getValue(String possibleName)
    {
        if (valueMap == null)
        {
            valueMap = new HashMap<String, InterestChargeTypeEnum>();
            for(InterestChargeTypeEnum interestChargeType: values())
                valueMap.put(interestChargeType.toString(), interestChargeType);
        }
        if(valueMap.get(possibleName)!=null)
            return valueMap.get(possibleName);
        else
            return valueMap.get("INVALID_CATALOG_STATUS");

    }
}
