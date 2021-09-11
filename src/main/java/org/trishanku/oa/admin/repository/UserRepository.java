package org.trishanku.oa.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.trishanku.oa.admin.entity.Role;
import org.trishanku.oa.admin.entity.TransactionStatusEnum;
import org.trishanku.oa.admin.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    List<User> findByRoles(Role role);
    List<User> findByRolesIn(List<Role> roleList);
    //List<User> findDistinctByUserIdAndRolesIn(List<Role> roleList);
    User findByRolesAndUserId(Role role,String userId);
    User findByRolesInAndUserId(List<Role> roles,String userId);
    Optional<User> findByUserId(String userId);
    List<User> findByRolesAndTransactionStatus(Role role, TransactionStatusEnum transactionStatus);
    List<User> findByRolesInAndTransactionStatus(List<Role> roles, TransactionStatusEnum transactionStatus);
}
