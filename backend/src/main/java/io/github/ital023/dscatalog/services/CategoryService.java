package io.github.ital023.dscatalog.services;

import io.github.ital023.dscatalog.dto.CategoryDTO;
import io.github.ital023.dscatalog.entities.Category;
import io.github.ital023.dscatalog.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll(){
        List<Category> list = categoryRepository.findAll();

        return list.stream().map(CategoryDTO::new).toList();
    }

}
