package org.trishanku.oa.admin.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.trishanku.oa.admin.entity.RM;
import org.trishanku.oa.admin.entity.TransactionStatusEnum;
import org.trishanku.oa.admin.jwtauthentication.configuration.service.JWTUtil;
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
    @Autowired
    JWTUtil jwtUtil;
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
        newRM.setUuid(UUID.randomUUID());
        newRM.setStatus(true);
        newRM.setCreationDetails(jwtUtil.extractUsernameFromRequest());
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
        currentRM.setModificationDetails(jwtUtil.extractUsernameFromRequest());

        return rmMapper.RMToRMDTO(rmRepository.save(currentRM));
    }

    @Override
    @Transactional
    public RMDTO authoriseRMUser(String rmId) {
        RM currentRM = rmRepository.findByRmId(rmId);
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
        RM currentRM = rmRepository.findByRmId(rmId);
        currentRM.setDeleteFlag(true);
        currentRM.setModificationDetails(jwtUtil.extractUsernameFromRequest());
        return rmMapper.RMToRMDTO(rmRepository.save(currentRM));
    }
}
