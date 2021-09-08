package org.trishanku.oa.admin.mapper;

import org.mapstruct.Mapper;
import org.trishanku.oa.admin.entity.Customer;
import org.trishanku.oa.admin.model.CustomerDTO;

@Mapper
public interface CustomerMapper {

    CustomerDTO CustomerToCustomerDTO(Customer customer);
    Customer CustomerDTOToCustomer(CustomerDTO customerDTO);
}
