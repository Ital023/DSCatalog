package io.github.ital023.dscatalog.services;


import io.github.ital023.dscatalog.dto.ProductDTO;
import io.github.ital023.dscatalog.entities.Product;
import io.github.ital023.dscatalog.repositories.ProductRepository;
import io.github.ital023.dscatalog.services.exceptions.DataBaseException;
import io.github.ital023.dscatalog.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public ProductDTO getById(Long id) {
        Optional<Product> obj = productRepository.findById(id);
        Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity Not Found"));

        return new ProductDTO(entity, entity.getCategories());
    }

    @Transactional
    public ProductDTO insert(ProductDTO productDTO) {
        Product entity = new Product();
        entity.setName(productDTO.getName());

        entity = productRepository.save(entity);

        return new ProductDTO(entity);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO productDTO) {
        try{
            Product entity = productRepository.getReferenceById(id);
            entity.setName(productDTO.getName());

            productRepository.save(entity);

            return new ProductDTO(entity);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Id not found: " + id);
        }
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
        try {
            productRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Falha de integridade referencial");
        }
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllPaged(PageRequest pageRequest) {
        Page<Product> list = productRepository.findAll(pageRequest);
        return list.map(x -> new ProductDTO(x));
    }

}
