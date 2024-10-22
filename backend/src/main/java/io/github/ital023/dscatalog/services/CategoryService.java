package io.github.ital023.dscatalog.services;

import io.github.ital023.dscatalog.dto.CategoryDTO;
import io.github.ital023.dscatalog.entities.Category;
import io.github.ital023.dscatalog.repositories.CategoryRepository;
import io.github.ital023.dscatalog.services.exceptions.DataBaseException;
import io.github.ital023.dscatalog.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public CategoryDTO getById(Long id) {
        Optional<Category> obj = categoryRepository.findById(id);
        Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity Not Found"));

        return new CategoryDTO(entity);
    }

    @Transactional
    public CategoryDTO insert(CategoryDTO categoryDTO) {
        Category entity = new Category();
        entity.setName(categoryDTO.getName());

        entity = categoryRepository.save(entity);

        return new CategoryDTO(entity);
    }

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO categoryDTO) {
        try{
            Category entity = categoryRepository.getReferenceById(id);
            entity.setName(categoryDTO.getName());

            categoryRepository.save(entity);

            return new CategoryDTO(entity);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Id not found: " + id);
        }
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
        try {
            categoryRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Falha de integridade referencial");
        }
    }

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        List<Category> list = categoryRepository.findAll();
        return list.stream().map(x -> new CategoryDTO(x)).toList();
    }



}


