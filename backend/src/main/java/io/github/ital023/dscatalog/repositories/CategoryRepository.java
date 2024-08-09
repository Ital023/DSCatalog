package io.github.ital023.dscatalog.repositories;

import io.github.ital023.dscatalog.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
