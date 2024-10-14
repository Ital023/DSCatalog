package io.github.ital023.dscatalog.repositories;

import io.github.ital023.dscatalog.entities.Category;
import io.github.ital023.dscatalog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

}
