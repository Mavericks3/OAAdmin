package org.trishanku.oa.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.trishanku.oa.admin.entity.Product;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    Product findByName(String name);

    @Query(value = "SELECT nextval('admin.\"ProductSequence\"')", nativeQuery=true)

    String getProductSequence();
}
