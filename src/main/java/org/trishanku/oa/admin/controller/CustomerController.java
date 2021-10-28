package org.trishanku.oa.admin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.trishanku.oa.admin.entity.Customer;
import org.trishanku.oa.admin.entity.TransactionStatusEnum;
import org.trishanku.oa.admin.mapper.CustomerMapper;
import org.trishanku.oa.admin.model.CustomerDTO;
import org.trishanku.oa.admin.repository.CustomerRepository;
import org.trishanku.oa.admin.service.CustomerService;

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

    @Autowired
    CustomerService customerService;

    @GetMapping()
    public ResponseEntity<List<CustomerDTO>> getCustomersList()
    {
        return new ResponseEntity<>(customerService.getAllCustomers(),HttpStatus.OK);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<CustomerDTO>> getPendingCustomersList()
    {
        return new ResponseEntity<>(customerService.getPendingCustomers(),HttpStatus.OK);
    }

    @GetMapping(path = "/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomerByCustomerId(@PathVariable(name="customerId") String customerId)
    {

        return new ResponseEntity<>(customerService.getCustomerById(customerId),HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<CustomerDTO> addCustomer(@RequestBody CustomerDTO customerDTO)
    {
        return new ResponseEntity<>(customerService.addCustomer(customerDTO),HttpStatus.CREATED);
    }

    @PutMapping(path="/{customerId}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable(name="customerId") String customerId, @RequestBody CustomerDTO customerDTO)
    {
        return new ResponseEntity<>(customerService.modifyCustomer(customerId,customerDTO),HttpStatus.ACCEPTED);
    }

    @DeleteMapping(path="/{customerId}")
    public ResponseEntity deleteCustomer(@PathVariable(name="customerId") String customerId)
    {
        return new ResponseEntity<>(customerService.deleteCustomer(customerId),HttpStatus.NO_CONTENT);

    }

    @PutMapping(path="/authorise/{customerId}")
    public ResponseEntity<CustomerDTO> authoriseCustomer(@PathVariable(name="customerId") String customerId)
    {
        return new ResponseEntity<>(customerService.authoriseCustomer(customerId),HttpStatus.ACCEPTED);
    }




}
