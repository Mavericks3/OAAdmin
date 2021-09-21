package org.trishanku.oa.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.trishanku.oa.admin.mapper.SBRMapper;
import org.trishanku.oa.admin.model.SBRDTO;
import org.trishanku.oa.admin.repository.SBRRepository;

import java.util.List;

@Service
public class SBRServiceImpl implements SBRService {

    @Autowired
    SBRRepository sbrRepository;
    @Autowired
    SBRMapper sbrMapper;

    @Override
    public List<SBRDTO> getAllSBRs() {
        return sbrMapper.SBRsToSBRDTOs(sbrRepository.findAll());
    }
}
