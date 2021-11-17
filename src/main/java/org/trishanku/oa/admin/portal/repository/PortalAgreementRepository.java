package org.trishanku.oa.admin.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.trishanku.oa.admin.entity.TransmissionStatusEnum;
import org.trishanku.oa.admin.portal.entity.PortalAgreement;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PortalAgreementRepository extends JpaRepository<PortalAgreement, UUID> {


    Optional<List<PortalAgreement>> findByTransmissionStatus(TransmissionStatusEnum transmissionStatus);
}
