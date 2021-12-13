package org.trishanku.oa.admin.entity;

import java.util.HashMap;
import java.util.Map;

public enum CurrencyEnum {
    JOD,MAD,AUD,BND,XCD,HKD,NZD,SGD,USD,AMD,EUR,XAF,XPF,CHF,XOF,ANG,DKK,TRY,MRU,GBP,GGP,SHP,ZAR,RUB,INR,ILS;

    private static Map<String, CurrencyEnum> valueMap;

    public static CurrencyEnum getValue(String possibleName)
    {
        if (valueMap == null)
        {
            valueMap = new HashMap<String, CurrencyEnum>();
            for(CurrencyEnum currency: values())
                valueMap.put(currency.toString(), currency);
        }
        return valueMap.get(possibleName);

    }

    }
