package org.trishanku.oa.admin.mapper;

import org.mapstruct.Mapper;
import org.trishanku.oa.admin.entity.Product;
import org.trishanku.oa.admin.model.ProductDTO;

import java.util.List;

@Mapper
public interface ProductMapper {

    ProductDTO ProductToProductDTO(Product product);
    List<ProductDTO> ProductsToProductDTOs(List<Product> products);
    Product ProductDTOToProduct(ProductDTO productDTO);
    List<Product> ProductDTOsToProducts(List<ProductDTO> productDTO);

}
