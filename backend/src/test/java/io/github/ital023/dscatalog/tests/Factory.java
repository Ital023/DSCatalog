package io.github.ital023.dscatalog.tests;

import io.github.ital023.dscatalog.dto.ProductDTO;
import io.github.ital023.dscatalog.entities.Category;
import io.github.ital023.dscatalog.entities.Product;

import java.time.Instant;

public class Factory {

    public static Product createProduct(){
        Product product = new Product(1L,"Phone","Good Phone", 1350.0 , "https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/6-big.jpg", Instant.parse("2020-07-14T10:00:00Z"));
        product.getCategories().add(new Category("Eletronics",2L));
        return product;
    }

    public static ProductDTO createProductDTO(){
        return new ProductDTO(createProduct());
    }


}
