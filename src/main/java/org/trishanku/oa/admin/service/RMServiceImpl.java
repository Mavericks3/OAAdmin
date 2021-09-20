package org.trishanku.oa.admin.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.trishanku.oa.admin.entity.RM;
import org.trishanku.oa.admin.entity.TransactionStatusEnum;
import org.trishanku.oa.admin.mapper.RMMapper;
import org.trishanku.oa.admin.model.RMDTO;
import org.trishanku.oa.admin.repository.RMRepository;

import java.util.*;


@Service
@Slf4j
public class RMServiceImpl implements RMService{


    @Autowired
    RMRepository rmRepository;

    @Autowired
    RMMapper rmMapper;

    @Override
    public List<RMDTO> getAllRMUsers() {
        return rmMapper.RMsToRMDTOs(rmRepository.findAll());
    }

    @Override
    public RMDTO getRMUserById(String rmId) {
        return rmMapper.RMToRMDTO(rmRepository.findByRmId((rmId)));
    }

    @Override
    public RMDTO addRMUser(RMDTO rmdto) {

        RM newRM = rmMapper.RMDTOToRM(rmdto);
        // to be changed once the user details are retrieved from JSON
        newRM.setUuid(UUID.randomUUID());
        newRM.setStatus(true);
        newRM.setCreationDetails("RAVIKANTH");
        return rmMapper.RMToRMDTO(rmRepository.save(newRM));

    }

    @Override
    public RMDTO modifyRMUser(String rmId, RMDTO rmDTO) {
        RM currentRM = rmRepository.findByRmId(rmId);
        currentRM.setName(rmDTO.getName());
        currentRM.setEmailAddress(rmDTO.getEmailAddress());
        currentRM.setJoiningDate(rmDTO.getJoiningDate());
        currentRM.setValidDate(rmDTO.getValidDate());
        currentRM.setExpiryDate(rmDTO.getExpiryDate());

        //set modification details once the details are fetched from JSON
        currentRM.setModificationDetails("RAVIKANTH");

        return rmMapper.RMToRMDTO(rmRepository.save(currentRM));
    }

    @Override
    public RMDTO authoriseRMUser(String rmId) {
        RM currentRM = rmRepository.findByRmId(rmId);
        //set authorization details once the details are fetched from JSON
        currentRM.setAuthorizationDetails("RAVIKANTH");
        return rmMapper.RMToRMDTO(rmRepository.save(currentRM));
    }

    @Override
    public List<RMDTO> getPendingRMUsers() {
        return rmMapper.RMsToRMDTOs(rmRepository.findByTransactionStatus(TransactionStatusEnum.PENDING));
    }

    @Override
    public RMDTO deleteRMUser(String rmId) {
        RM currentRM = rmRepository.findByRmId(rmId);
        currentRM.setStatus(false);
        //set modification details once the details are fetched from JSON
        currentRM.setModificationDetails("RAVIKANTH");
        return rmMapper.RMToRMDTO(rmRepository.save(currentRM));
    }
}
