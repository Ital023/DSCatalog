package io.github.ital023.dscatalog.repositories;

import io.github.ital023.dscatalog.entities.Product;
import io.github.ital023.dscatalog.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTests {

    //Autowired pois estou testando o banco real, depois é dado rollback
    //Pode usar dados reais do banco, pois ele vai ser carregado
    @Autowired
    private ProductRepository repository;

    private Long existingId;
    private Long nonExistingId;
    private Long countTotalProducts;


    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 1000L;
        countTotalProducts = 25L;
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists() {

        repository.deleteById(existingId);

        Optional<Product> result = repository.findById(existingId);

        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void saveShouldPersistWithAutoIncrementWhenIdIsNull() {
        Product product = Factory.createProduct();
        product.setId(null);

        product = repository.save(product);

        Assertions.assertNotNull(product.getId());
        Assertions.assertEquals(countTotalProducts + 1, product.getId());
    }

    @Test
    public void findByIdShouldReturnProductNotNullWhenIdExists() {
        Optional<Product> product = repository.findById(existingId);

        Assertions.assertTrue(product.isPresent());
    }

    @Test
    public void findByIdShouldReturnProductNullWhenIdNonExists() {
        Optional<Product> product = repository.findById(nonExistingId);

        Assertions.assertTrue(product.isEmpty());
    }




}
