package org.trishanku.oa.admin.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.trishanku.oa.admin.entity.TransactionStatusEnum;
import org.trishanku.oa.admin.entity.TransmissionStatusEnum;
import org.trishanku.oa.admin.notification.entity.NotificationStatusEnum;
import org.trishanku.oa.admin.portal.entity.PortalCustomer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PortalCustomerRepository extends JpaRepository<PortalCustomer, UUID> {


    Optional<List<PortalCustomer>> findByTransmissionStatus(TransmissionStatusEnum transmissionStatus);
}
