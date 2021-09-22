package org.trishanku.oa.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.trishanku.oa.admin.entity.Customer;
import org.trishanku.oa.admin.entity.SBR;
import org.trishanku.oa.admin.entity.TransactionStatusEnum;
import org.trishanku.oa.admin.mapper.SBRMapper;
import org.trishanku.oa.admin.model.SBRDTO;
import org.trishanku.oa.admin.repository.CustomerRepository;
import org.trishanku.oa.admin.repository.SBRRepository;

import java.util.Collections;
import java.util.List;

@Service
public class SBRServiceImpl implements SBRService {

    @Autowired
    SBRRepository sbrRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    SBRMapper sbrMapper;

    @Override
    public List<SBRDTO> getAllSBRs() {

        return sbrMapper.SBRsToSBRDTOs(sbrRepository.findAll());
    }

    @Override
    public List<SBRDTO> getAllPendingSBRs() {

        return sbrMapper.SBRsToSBRDTOs(sbrRepository.findByTransactionStatus(TransactionStatusEnum.PENDING));
    }

    @Override
    public List<SBRDTO> getAllMasterSBRs() {

        return sbrMapper.SBRsToSBRDTOs(sbrRepository.findByTransactionStatus(TransactionStatusEnum.MASTER));
    }

    @Override
    public SBRDTO save(SBRDTO sbrdto) {
        SBR sbr = sbrRepository.findBySbrId(sbrdto.getSbrId());
        if(sbr == null) throw new RuntimeException("SBR with id " + sbrdto.getSbrId() + " does not exist");
        return  sbrMapper.SBRToSBRDTO(sbrRepository.save(sbr));
    }

    @Override
    public SBRDTO authorise(SBRDTO sbrdto) {
        SBR sbr = sbrRepository.findBySbrId(sbrdto.getSbrId());
        if(sbr == null) throw new RuntimeException("SBR with id " + sbrdto.getSbrId() + " does not exist");
        //TO BE MODIFIED ONCE USER DETAILS ARE RETRIEVED FROM JWT
        sbr.setAuthorizationDetails("RAVIKANTH");
        return  sbrMapper.SBRToSBRDTO(sbrRepository.save(sbr));
    }

    @Override
    public SBRDTO getSBRById(String sbrId) {
        return sbrMapper.SBRToSBRDTO(sbrRepository.findBySbrId(sbrId));
    }

    @Override
    public SBRDTO delete(SBRDTO sbrdto) {
        SBR sbr = sbrRepository.findBySbrId(sbrdto.getSbrId());
        if(sbr == null) throw new RuntimeException("SBR with id " + sbrdto.getSbrId() + " does not exist");
        sbr.setStatus(false);
        return  sbrMapper.SBRToSBRDTO(sbrRepository.save(sbr));
    }

    @Override
    public List<SBRDTO> getSBRsByAnchorCustomer(String anchorCustomerId) {
        Customer anchorCustomer = customerRepository.findByCustomerId(anchorCustomerId).orElseThrow(() -> new RuntimeException("customer with id " + anchorCustomerId + " does not exist"));
        List<SBR> sbrList = sbrRepository.findByAnchorCustomerId(anchorCustomer);
        return sbrMapper.SBRsToSBRDTOs(sbrList);
    }
}
