package org.trishanku.oa.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.trishanku.oa.admin.entity.User;

import java.util.UUID;

public interface UserInterface extends JpaRepository<User, UUID> {
}
