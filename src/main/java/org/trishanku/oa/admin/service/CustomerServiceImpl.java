package org.trishanku.oa.admin.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.trishanku.oa.admin.entity.Customer;
import org.trishanku.oa.admin.entity.TransactionStatusEnum;
import org.trishanku.oa.admin.jwtauthentication.configuration.service.JWTUtil;
import org.trishanku.oa.admin.mapper.CustomerMapper;
import org.trishanku.oa.admin.model.CustomerDTO;
import org.trishanku.oa.admin.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {


    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CustomerMapper customerMapper;
    @Autowired
    JWTUtil jwtUtil;

    @Override
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        if(customers.size()==0) throw new RuntimeException("No customers exists in the system");
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        customers.forEach((customer) -> customerDTOS.add(customerMapper.customerToCustomerDTO(customer)));
        return customerDTOS;

    }

    @Override
    public List<CustomerDTO> getAllCustomersPaginated(int page, int size) {
       // pagination to be implemented
        List<Customer> customers = customerRepository.findAll();
        if(customers.size()==0) throw new RuntimeException("No customers exists in the system");
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        customers.forEach((customer) -> customerDTOS.add(customerMapper.customerToCustomerDTO(customer)));
        return customerDTOS;

    }

    @Override
    public CustomerDTO getCustomerById(String customerId)
    {
        Optional<Customer> customer = customerRepository.findByCustomerId(customerId);
        if(customer.isEmpty()) throw new RuntimeException("customer with id " + customerId + " not found");
        log.info("customer retrieved from the database is " + customer.get());
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer.get());
        log.info("customerDTO transmitted to the user is " + customerDTO);
        return customerDTO;

    }

    @Override
    public CustomerDTO addCustomer(CustomerDTO customerDTO)
    {
        // to check if a customer with the given id already exists
        if(customerRepository.findByCustomerId(customerDTO.getCustomerId()).isPresent()) throw new RuntimeException("customer with id " + customerDTO.getCustomerId() + " already exists");

        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
        customer.setUuid(UUID.randomUUID());
        customer.setCreationDetails(jwtUtil.extractUsernameFromRequest());

        Customer savedCustomer = customerRepository.save(customer);
        CustomerDTO savedCustomerDTO = customerMapper.customerToCustomerDTO(savedCustomer);
        return savedCustomerDTO;

    }

    @Override
    public CustomerDTO modifyCustomer(String customerId, CustomerDTO customerDTO)
    {
        log.debug("in update customer method " + customerId);
        // to check if a customer with the given does not exist
        if(customerRepository.findByCustomerId(customerId).isEmpty()) throw new RuntimeException("customer with id " + customerId + " does not exist");
        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
        Customer currentCustomerDetails = customerRepository.findByCustomerId(customerId).get();

        //below code to be refactored
        customer.setUuid(currentCustomerDetails.getUuid());
        customer.setCreatedUser(currentCustomerDetails.getCreatedUser());
        customer.setCreatedDate(currentCustomerDetails.getCreatedDate());
        customer.setCustomerId(customerId);

        customer.setModificationDetails(jwtUtil.extractUsernameFromRequest());
        log.debug("customer to be saved is " + customer);
        Customer savedCustomer = customerRepository.save(customer);
        CustomerDTO savedCustomerDTO = customerMapper.customerToCustomerDTO(savedCustomer);
        return savedCustomerDTO;

    }

    @Override
    @Transactional
    public CustomerDTO authoriseCustomer(String customerId)
    {
        log.debug("in authorise customer method " + customerId);
        // to check if a customer with the given does not exist
        if(customerRepository.findByCustomerId(customerId).isEmpty()) throw new RuntimeException("customer with id " + customerId + " does not exist");
        Customer customer = customerRepository.findByCustomerId(customerId).get();
        customer.setAuthorizationDetails(jwtUtil.extractUsernameFromRequest());
        Customer savedCustomer = customerRepository.save(customer);
        if(savedCustomer.isDeleteFlag()) customerRepository.delete(savedCustomer);
        CustomerDTO savedCustomerDTO = customerMapper.customerToCustomerDTO(savedCustomer);

        return savedCustomerDTO;

    }

    @Override
    public List<CustomerDTO> getPendingCustomers() {

        List<Customer> customers = customerRepository.findByTransactionStatus(TransactionStatusEnum.PENDING);
        if(customers.size()==0) throw new RuntimeException("No pending customers exists in the system");
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        customers.forEach((customer) -> customerDTOS.add(customerMapper.customerToCustomerDTO(customer)));
        return customerDTOS;

    }

    @Override
    public CustomerDTO deleteCustomer(String customerId)
    {
        log.debug("in delete customer method " + customerId);
        // to check if a customer with the given does not exist
        if(customerRepository.findByCustomerId(customerId).isEmpty()) throw new RuntimeException("customer with id " + customerId + " does not exist");
        Customer toDeleteCustomer = customerRepository.findByCustomerId(customerId).get();
        toDeleteCustomer.setDeleteFlag(true);
        toDeleteCustomer.setModificationDetails(jwtUtil.extractUsernameFromRequest());
        customerRepository.save(toDeleteCustomer);
        return customerMapper.customerToCustomerDTO(toDeleteCustomer);

    }
}
