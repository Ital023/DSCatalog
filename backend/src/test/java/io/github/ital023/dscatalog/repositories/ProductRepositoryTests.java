package io.github.ital023.dscatalog.repositories;

import io.github.ital023.dscatalog.entities.Product;
import io.github.ital023.dscatalog.services.exceptions.ResourceNotFoundException;
import io.github.ital023.dscatalog.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository repository;

    private long existId;
    private long countTotalProducts;

    @BeforeEach
    void setUp() throws Exception {
        existId = 1L;
        countTotalProducts = 25L;
    }

    @Test
    public void saveShouldPersistWithAutoIncrementWhenIdIsNull(){

        Product product = Factory.createProduct();
        product.setId(null);

        product = repository.save(product);

        Assertions.assertNotNull(product.getId());
        Assertions.assertEquals(countTotalProducts + 1, product.getId());
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExist(){
        //Arrange

        //Act
        repository.deleteById(existId);

        //Assert
        Optional<Product> result = repository.findById(existId);
        Assertions.assertFalse(result.isPresent());
    }



}
