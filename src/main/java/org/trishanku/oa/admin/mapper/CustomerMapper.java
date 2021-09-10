package org.trishanku.oa.admin.mapper;

import org.mapstruct.Mapper;
import org.trishanku.oa.admin.entity.Customer;
import org.trishanku.oa.admin.model.CustomerDTO;

import java.util.List;

@Mapper
public interface CustomerMapper {

    Customer customerDTOToCustomer(CustomerDTO customerDTO);
    CustomerDTO customerToCustomerDTO(Customer customer);
    List<CustomerDTO> customersToCustomerDTOs(List<Customer> customers);
    List<Customer> customerDTOsToCustomers(List<CustomerDTO> customerDTOS);
}
