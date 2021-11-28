package org.trishanku.oa.admin.portal.service;

import org.trishanku.oa.admin.portal.entity.PortalMessageType;

public interface PortalService {

    void addCustomer(Object result);
    void addRM(Object result);
    void addCustomerAdmin(Object result);
    void addCustomerUser(Object result);
    void addAgreement(Object result);
    void addSBR(Object result);
    void add(String message, PortalMessageType messageType);
    void addBankUser(Object result);
}
