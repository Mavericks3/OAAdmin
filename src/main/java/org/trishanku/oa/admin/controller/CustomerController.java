package org.trishanku.oa.admin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.trishanku.oa.admin.entity.Customer;
import org.trishanku.oa.admin.mapper.CustomerMapper;
import org.trishanku.oa.admin.model.CustomerDTO;
import org.trishanku.oa.admin.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerMapper customerMapper;

    @GetMapping()
    public ResponseEntity<List<CustomerDTO>> getCustomersList()
    {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        customers.forEach((customer) -> customerDTOS.add(customerMapper.customerToCustomerDTO(customer)));
        return new ResponseEntity<>(customerDTOS,HttpStatus.OK);
    }

    @GetMapping(path = "/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomerByCustomerId(@PathVariable(name="customerId") String customerId)
    {
        Optional<Customer> customer = customerRepository.findByCustomerId(customerId);
        if(customer.isEmpty()) throw new RuntimeException("customer with id " + customerId + " not found");
        log.info("customer retrieved from the database is " + customer.get());
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer.get());
        log.info("customerDTO transmitted to the user is " + customerDTO);
        return new ResponseEntity<>(customerDTO,HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<CustomerDTO> addCustomer(@RequestBody CustomerDTO customerDTO)
    {
        // to check if a customer with the given id already exists
         if(customerRepository.findByCustomerId(customerDTO.getCustomerId()).isPresent()) throw new RuntimeException("customer with id " + customerDTO.getCustomerId() + " already exists");

        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
        customer.setUuid(UUID.randomUUID());
        //below line to be changed to retrieve the user name from request
        customer.setCreationDetails("ToBeChanged");

        Customer savedCustomer = customerRepository.save(customer);
        CustomerDTO savedCustomerDTO = customerMapper.customerToCustomerDTO(savedCustomer);
        return new ResponseEntity<>(savedCustomerDTO,HttpStatus.CREATED);
    }

    @PutMapping(path="/{customerId}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable(name="customerId") String customerId, @RequestBody CustomerDTO customerDTO)
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
        //below line to be changed to retrieve the user name from request
        customer.setModificationDetails("ToBeChanged");
        log.debug("customer to be saved is " + customer);
        Customer savedCustomer = customerRepository.save(customer);
        CustomerDTO savedCustomerDTO = customerMapper.customerToCustomerDTO(savedCustomer);
        return new ResponseEntity<>(savedCustomerDTO,HttpStatus.ACCEPTED);
    }

    @DeleteMapping(path="/{customerId}")
    public ResponseEntity deleteCustomer(@PathVariable(name="customerId") String customerId)
    {
        log.debug("in delete customer method " + customerId);
        // to check if a customer with the given does not exist
        if(customerRepository.findByCustomerId(customerId).isEmpty()) throw new RuntimeException("customer with id " + customerId + " does not exist");
        customerRepository.deleteById(customerRepository.findByCustomerId(customerId).get().getUuid());
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(path="/authorise/{customerId}")
    public ResponseEntity<CustomerDTO> authoriseCustomer(@PathVariable(name="customerId") String customerId)
    {
        log.debug("in authorise customer method " + customerId);
        // to check if a customer with the given does not exist
        if(customerRepository.findByCustomerId(customerId).isEmpty()) throw new RuntimeException("customer with id " + customerId + " does not exist");
        Customer customer = customerRepository.findByCustomerId(customerId).get();
        //below line to be changed to retrieve the user name from request
        customer.setAuthorizationDetails("ToBeChanged");
        Customer savedCustomer = customerRepository.save(customer);
        CustomerDTO savedCustomerDTO = customerMapper.customerToCustomerDTO(savedCustomer);
        return new ResponseEntity<>(savedCustomerDTO,HttpStatus.ACCEPTED);
    }




}
