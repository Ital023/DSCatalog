package io.github.ital023.dscatalog.repositories;

import io.github.ital023.dscatalog.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
