package org.trishanku.oa.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trishanku.oa.admin.entity.Product;
import org.trishanku.oa.admin.entity.Role;
import org.trishanku.oa.admin.mapper.ProductMapper;
import org.trishanku.oa.admin.model.ProductDTO;
import org.trishanku.oa.admin.model.RoleDTO;
import org.trishanku.oa.admin.repository.ProductRepository;


import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductMapper productMapper;

    @GetMapping
    @PreAuthorize("hasAnyAuthority({'SUPER_ADMIN','BANK_ADMIN_MAKER','BANK_ADMIN_VIEWER','BANK_ADMIN_CHECKER' })")
    @Transactional
    public ResponseEntity<List<ProductDTO>> getProducts()
    {
        List<Product> productList = productRepository.findAll();
       return new ResponseEntity<>(productMapper.ProductsToProductDTOs(productList), HttpStatus.OK);
    }
}
