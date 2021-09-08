package org.trishanku.oa.admin.controller;

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
        customers.forEach((customer) -> customerDTOS.add(customerMapper.CustomerToCustomerDTO(customer)));
        return new ResponseEntity<>(customerDTOS,HttpStatus.OK);
    }

    @GetMapping(path = "/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomerByCustomerId(@PathVariable(name="customerId") String customerId)
    {
        Optional<Customer> customer = customerRepository.findByCustomerId(customerId);
        if(customer.isEmpty()) throw new RuntimeException("customer with id " + customerId + " not found");
        CustomerDTO customerDTO = customerMapper.CustomerToCustomerDTO(customer.get());
        return new ResponseEntity<>(customerDTO,HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<CustomerDTO> addCustomer(@RequestBody CustomerDTO customerDTO)
    {
        // to check if a customer with the given id already exists
         if(customerRepository.findByCustomerId(customerDTO.getCustomerId()).isPresent()) throw new RuntimeException("customer with id " + customerDTO.getCustomerId() + " already exists");

        Customer customer = customerMapper.CustomerDTOToCustomer(customerDTO);
        customer.setUuid(UUID.randomUUID());
        Customer savedCustomer = customerRepository.save(customer);
        CustomerDTO savedCustomerDTO = customerMapper.CustomerToCustomerDTO(savedCustomer);
        return new ResponseEntity<>(savedCustomerDTO,HttpStatus.CREATED);
    }



}
