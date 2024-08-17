package io.github.ital023.dscatalog.repositories;

import io.github.ital023.dscatalog.entities.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository repository;

    @Test
    public void deleteShouldDeleteObjectWhenIdExist(){
        //Arrange
        long existId = 1L;

        //Act
        repository.deleteById(existId);

        Optional<Product> result = repository.findById(existId);
        Assertions.assertFalse(result.isPresent());
    }

}
