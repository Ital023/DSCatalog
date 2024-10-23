package io.github.ital023.dscatalog.repositories;

import io.github.ital023.dscatalog.entities.Product;
import io.github.ital023.dscatalog.projetions.ProductProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(nativeQuery = true,
    value = """
            SELECT DISTINCT TB_PRODUCT.id, TB_PRODUCT.name
            FROM TB_PRODUCT
            INNER JOIN TB_PRODUCT_CATEGORY ON TB_PRODUCT.id =  TB_PRODUCT_CATEGORY.product_id
            WHERE (:categoryIds IS NULL OR TB_PRODUCT_CATEGORY.category_id IN :categoryIds)
            AND LOWER(TB_PRODUCT.name) LIKE LOWER(CONCAT('%',:name,'%'))
            ORDER BY TB_PRODUCT.name
            """, countQuery = """
            SELECT COUNT(*) FROM (
            SELECT DISTINCT TB_PRODUCT.id, TB_PRODUCT.name
            FROM TB_PRODUCT
            INNER JOIN TB_PRODUCT_CATEGORY ON TB_PRODUCT.id =  TB_PRODUCT_CATEGORY.product_id
            WHERE (:categoryIds IS NULL OR TB_PRODUCT_CATEGORY.category_id IN :categoryIds)
            AND LOWER(TB_PRODUCT.name) LIKE LOWER(CONCAT('%',:name,'%'))
            ORDER BY TB_PRODUCT.name
            ) AS tb_result
            """)
    Page<ProductProjection> searchProducts(List<Long> categoryIds, String name, Pageable pageable);


    @Query("SELECT obj FROM Product obj JOIN FETCH obj.categories WHERE obj.id IN :productsIds ORDER BY obj.name")
    List<Product> searchProductsWithCategories(List<Long> productsIds);
}
