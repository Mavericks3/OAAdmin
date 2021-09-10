package org.trishanku.oa.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.trishanku.oa.admin.entity.Role;
import org.trishanku.oa.admin.entity.User;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    List<User> findByRoles(Role role);
    User findByRolesAndUserId(Role role,String userId);
}
