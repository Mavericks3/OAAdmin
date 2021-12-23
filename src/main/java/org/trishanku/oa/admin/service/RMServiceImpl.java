package org.trishanku.oa.admin.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.trishanku.oa.admin.entity.Customer;
import org.trishanku.oa.admin.entity.RM;
import org.trishanku.oa.admin.entity.TransactionStatusEnum;
import org.trishanku.oa.admin.jwtauthentication.configuration.service.JWTUtil;
import org.trishanku.oa.admin.mapper.RMMapper;
import org.trishanku.oa.admin.model.RMDTO;
import org.trishanku.oa.admin.repository.CustomerRepository;
import org.trishanku.oa.admin.repository.RMRepository;

import java.util.*;


@Service
@Slf4j
public class RMServiceImpl implements RMService{


    @Autowired
    RMRepository rmRepository;
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    RMMapper rmMapper;
    @Autowired
    JWTUtil jwtUtil;
    @Autowired
    ObjectMapper objectMapper;
    @Override
    public List<RMDTO> getAllRMUsers() {
        return rmMapper.RMsToRMDTOs(rmRepository.findAll());
    }

    @Override
    public RMDTO getRMUserById(String rmId) {
        return rmMapper.RMToRMDTO(rmRepository.findByRmId((rmId)).get());
    }

    @Override
    public RMDTO addRMUser(RMDTO rmdto) {
        if(rmRepository.findByRmId(rmdto.getRmId()).isPresent()) throw  new RuntimeException("RM with id " + rmdto.getRmId() + " already exists");
        RM newRM = rmMapper.RMDTOToRM(rmdto);
        newRM.setUuid(UUID.randomUUID());
        List<Customer> rmCustomers = new ArrayList<Customer>();
       rmdto.getCustomers().forEach(customer -> {
           Optional<Customer> customerTemp = customerRepository.findByCustomerIdAndTransactionStatus(customer.getCustomerId(),TransactionStatusEnum.MASTER);
           if(customerTemp.isPresent())
               rmCustomers.add(customerTemp.get());
       });
       //if(rmCustomers.size()!=0) newRM.setCustomers(rmCustomers);
        newRM.setCustomers(rmCustomers);
        newRM.setCreationDetails(jwtUtil.extractUsernameFromRequest());
        RM savedRM = rmRepository.save(newRM);
        return rmMapper.RMToRMDTO(savedRM);

    }

    @Override
    public RMDTO modifyRMUser(String rmId, RMDTO rmDTO) {
        if(rmRepository.findByRmId(rmId).isEmpty()) throw  new RuntimeException("RM with id " + rmId + " does not exist");
        RM currentRM = rmRepository.findByRmId(rmId).get();
        currentRM.setFirstName(rmDTO.getFirstName());
        currentRM.setLastName(rmDTO.getLastName());
        currentRM.setEmailAddress(rmDTO.getEmailAddress());
        currentRM.setJoiningDate(rmDTO.getJoiningDate());
        currentRM.setEffectiveDate(rmDTO.getEffectiveDate());
        currentRM.setExpiryDate(rmDTO.getExpiryDate());
        currentRM.setModificationDetails(jwtUtil.extractUsernameFromRequest());
        List<Customer> rmCustomers = new ArrayList<Customer>();
        rmDTO.getCustomers().forEach(customer -> {
            Optional<Customer> customerTemp = customerRepository.findByCustomerIdAndTransactionStatus(customer.getCustomerId(),TransactionStatusEnum.MASTER);
            if(customerTemp.isPresent())
                rmCustomers.add(customerTemp.get());
        });
        //if(rmCustomers.size()!=0) currentRM.setCustomers(rmCustomers);
        currentRM.setCustomers(rmCustomers);
        return rmMapper.RMToRMDTO(rmRepository.save(currentRM));
    }

    @Override
    @Transactional
    public RMDTO authoriseRMUser(String rmId) {
        if(rmRepository.findByRmId(rmId).isEmpty()) throw  new RuntimeException("RM with id " + rmId + " does not exist");
        RM currentRM = rmRepository.findByRmId(rmId).get();
        currentRM.setAuthorizationDetails(jwtUtil.extractUsernameFromRequest());
        RM savedRM = rmRepository.save(currentRM);
        if(currentRM.isDeleteFlag()) rmRepository.delete(savedRM);
        return rmMapper.RMToRMDTO(savedRM);
    }

    @Override
    public List<RMDTO> getPendingRMUsers() {
        return rmMapper.RMsToRMDTOs(rmRepository.findByTransactionStatus(TransactionStatusEnum.PENDING));
    }

    @Override
    public RMDTO deleteRMUser(String rmId) {
        RM currentRM = rmRepository.findByRmId(rmId).get();
        currentRM.setDeleteFlag(true);
        currentRM.setModificationDetails(jwtUtil.extractUsernameFromRequest());
        return rmMapper.RMToRMDTO(rmRepository.save(currentRM));
    }

    @Override
    public String getNewReference() {

        String rmSequence = rmRepository.getRMSequence();
        return "RM" + Calendar.getInstance().get(Calendar.YEAR) + rmSequence;
    }

}
