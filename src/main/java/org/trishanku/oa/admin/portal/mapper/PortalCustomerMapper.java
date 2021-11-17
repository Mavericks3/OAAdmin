package org.trishanku.oa.admin.portal.mapper;

import org.mapstruct.Mapper;
import org.trishanku.oa.admin.entity.Customer;
import org.trishanku.oa.admin.portal.entity.PortalCustomer;

@Mapper
public interface PortalCustomerMapper {

    PortalCustomer CustomerToPortalCustomer(Customer customer);
}
