package org.trishanku.oa.admin.portal.mapper;

import org.mapstruct.Mapper;
import org.trishanku.oa.admin.entity.Agreement;
import org.trishanku.oa.admin.portal.entity.PortalAgreement;

@Mapper
public interface PortalAgreementMapper {

    PortalAgreement AgreementToPortalAgreement(Agreement agreement);
}
