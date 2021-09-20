package org.trishanku.oa.admin.mapper;

import org.mapstruct.Mapper;
import org.trishanku.oa.admin.entity.Agreement;
import org.trishanku.oa.admin.model.AgreementDTO;

import java.util.List;

@Mapper
public interface AgreementMapper {

    Agreement AgreementDTOToAgreement(AgreementDTO agreementDTO);
    AgreementDTO AgreementToAgreementDTO(Agreement agreement);
    List<Agreement> AgreementDTOsToAgreements(List<AgreementDTO> agreementDTOs);
    List<AgreementDTO> AgreementsToAgreementDTOs(List<Agreement> agreements);

}
