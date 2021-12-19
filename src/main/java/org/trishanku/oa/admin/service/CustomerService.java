package org.trishanku.oa.admin.service;

import org.springframework.stereotype.Service;
import org.trishanku.oa.admin.model.CustomerDTO;


import java.util.List;


public interface CustomerService {


    List<CustomerDTO> getAllCustomers();

    List<CustomerDTO> getAllCustomersPaginated(int page, int size);

    CustomerDTO getCustomerById(String customerId);

    CustomerDTO addCustomer(CustomerDTO customerDTO);

    CustomerDTO modifyCustomer(String customerId, CustomerDTO customerDTO);

    CustomerDTO authoriseCustomer(String customerId);

    List<CustomerDTO> getPendingCustomers();

    CustomerDTO deleteCustomer(String customerId);

    String getNewReference();
}
