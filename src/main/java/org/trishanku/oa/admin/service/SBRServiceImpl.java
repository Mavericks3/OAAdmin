package org.trishanku.oa.admin.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.trishanku.oa.admin.entity.*;
import org.trishanku.oa.admin.jwtauthentication.configuration.service.JWTUtil;
import org.trishanku.oa.admin.mapper.SBRMapper;
import org.trishanku.oa.admin.model.SBRDTO;
import org.trishanku.oa.admin.repository.AgreementRepository;
import org.trishanku.oa.admin.repository.CustomerRepository;
import org.trishanku.oa.admin.repository.RMRepository;
import org.trishanku.oa.admin.repository.SBRRepository;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class SBRServiceImpl implements SBRService {

    @Autowired
    SBRRepository sbrRepository;
    @Autowired
    SBRValidationService sbrValidationService;
    @Autowired
    RMRepository rmRepository;
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AgreementRepository agreementRepository;

    @Autowired
    SBRMapper sbrMapper;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    JWTUtil jwtUtil;

    @Override
    public List<SBRDTO> getAllSBRs() {

        return sbrMapper.SBRsToSBRDTOs(sbrRepository.findAll());
    }

    @Override
    public List<SBRDTO> getAllPendingSBRs() {

        return sbrMapper.SBRsToSBRDTOs(sbrRepository.findByTransactionStatus(TransactionStatusEnum.PENDING).get());
    }

    @Override
    public List<SBRDTO> getAllMasterSBRs() {

        return sbrMapper.SBRsToSBRDTOs(sbrRepository.findByTransactionStatus(TransactionStatusEnum.MASTER).get());
    }

    @Override
    public SBRDTO addSBR(SBRDTO sbrdto) {
        if (!sbrValidationService.isValid(sbrdto)) throw new RuntimeException("validation failed on the request");
        SBR sbr = sbrMapper.SBRDTOToSBR(sbrdto);
        sbr.setUuid(UUID.randomUUID());

        sbr.setAgreement(agreementRepository.findByContractReferenceNumber(sbrdto.getAgreement().getContractReferenceNumber()).get());
        sbr.setAnchorCustomer(customerRepository.findByCustomerId(sbrdto.getAnchorCustomer().getCustomerId()).get());
        sbr.setCounterParty(customerRepository.findByCustomerId(sbrdto.getCounterParty().getCustomerId()).get());
        sbr.setRm(rmRepository.findByRmId(sbrdto.getRm().getRmId()).get());
        sbr.setCreationDetails(jwtUtil.extractUsernameFromRequest());
        sbr.setStatus(true);
        return sbrMapper.SBRToSBRDTO(sbrRepository.save(sbr));
    }

    @Override
    public SBRDTO editSBR(SBRDTO sbrdto) {
        Optional<SBR> existingSBR = sbrRepository.findBySbrId(sbrdto.getSbrId());
        if (existingSBR.isEmpty()) throw new RuntimeException("SBR with id " + sbrdto.getSbrId() + " does not exist");
        if (!sbrValidationService.isValidModification(sbrdto))
            throw new RuntimeException("validation failed on the request");
        SBR sbr = sbrMapper.SBRDTOToSBR(sbrdto);
        sbr.setUuid(sbrRepository.findBySbrId(sbr.getSbrId()).get().getUuid());
        sbr.setAgreement(agreementRepository.findByContractReferenceNumber(sbrdto.getAgreement().getContractReferenceNumber()).get());
        sbr.setAnchorCustomer(customerRepository.findByCustomerId(sbrdto.getAnchorCustomer().getCustomerId()).get());
        sbr.setCounterParty(customerRepository.findByCustomerId(sbrdto.getCounterParty().getCustomerId()).get());
        sbr.setRm(rmRepository.findByRmId(sbrdto.getRm().getRmId()).get());
        sbr.setCreatedDate(existingSBR.get().getCreatedDate());
        sbr.setCreatedUser(existingSBR.get().getCreatedUser());
        sbr.setModificationDetails(jwtUtil.extractUsernameFromRequest());
        return sbrMapper.SBRToSBRDTO(sbrRepository.save(sbr));
    }

    @Override
    public SBRDTO authoriseSBR(SBRDTO sbrdto) {
        SBR sbr = sbrRepository.findBySbrId(sbrdto.getSbrId()).get();
        if (sbr == null) throw new RuntimeException("SBR with id " + sbrdto.getSbrId() + " does not exist");
        if (!(sbrValidationService.isValidAuthorization(sbrdto)))
            throw new RuntimeException("validation failed on the request");

        // logic to set Limit allocated and unallocated amounts on agreement

        BigDecimal sbrsTotalLimitAmount = new BigDecimal(0);
        Agreement agreement = sbrRepository.findBySbrId(sbrdto.getSbrId()).get().getAgreement();
        sbrRepository.findByAgreementAndTransactionStatus(agreement, TransactionStatusEnum.MASTER).ifPresent(sbrList -> {
            sbrList.forEach(sbrItem -> {
                sbrsTotalLimitAmount.add(sbrItem.getLimitAmount());
            });

        });

        BigDecimal limitAllocatedAmount = sbrsTotalLimitAmount.add(sbrRepository.findBySbrId(sbrdto.getSbrId()).get().getLimitAmount());
        agreement.setLimitAllocatedAmount(limitAllocatedAmount);
        agreement.setLimitUnallocatedAmount(agreement.getLimitAmount().subtract(limitAllocatedAmount));
        agreementRepository.save(agreement);
        // logic to set Limit allocated and unallocated amounts on agreement - ends here
        sbr.setAuthorizationDetails(jwtUtil.extractUsernameFromRequest());
        SBR save = sbrRepository.save(sbr);

        try {
            log.info("saved sbr is " + objectMapper.writeValueAsString(save));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return sbrMapper.SBRToSBRDTO(save);
    }

    @Override
    public SBRDTO getSBRById(String sbrId) {
        if (sbrRepository.findBySbrId(sbrId).isEmpty())
            throw new RuntimeException("SBR with id " + sbrId + " does not exist");
        return sbrMapper.SBRToSBRDTO(sbrRepository.findBySbrId(sbrId).get());
    }

    @Override
    public SBRDTO deleteSBR(SBRDTO sbrdto) {
        SBR sbr = sbrRepository.findBySbrId(sbrdto.getSbrId()).get();
        if (sbr == null) throw new RuntimeException("SBR with id " + sbrdto.getSbrId() + " does not exist");
        sbr.setStatus(false);
        return sbrMapper.SBRToSBRDTO(sbrRepository.save(sbr));
    }

    @Override
    public List<SBRDTO> getSBRsByAnchorCustomer(String anchorCustomerId) {
        Customer anchorCustomer = customerRepository.findByCustomerId(anchorCustomerId).orElseThrow(() -> new RuntimeException("customer with id " + anchorCustomerId + " does not exist"));
        List<SBR> sbrList = sbrRepository.findByAnchorCustomer(anchorCustomer).get();
        return sbrMapper.SBRsToSBRDTOs(sbrList);
    }

    @Override
    public List<SBRDTO> getSBRsByCounterParty(String counterPartyId) {
        Customer counterParty = customerRepository.findByCustomerId(counterPartyId).orElseThrow(() -> new RuntimeException("customer with id " + counterPartyId + " does not exist"));
        List<SBR> sbrList = sbrRepository.findByCounterParty(counterParty).get();
        return sbrMapper.SBRsToSBRDTOs(sbrList);
    }

    @Override
    public String getNewReference() {

        String sbrSequence = sbrRepository.getSBRSequence();
        return "SBR" + Calendar.getInstance().get(Calendar.YEAR) + sbrSequence;
    }
}
