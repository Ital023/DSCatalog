package io.github.ital023.dscatalog.service;

import io.github.ital023.dscatalog.repositories.ProductRepository;
import io.github.ital023.dscatalog.services.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository repository;

    private long existingId;
    private long NonexistingId;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        NonexistingId = 1000L;

        Mockito.doNothing().when(repository).deleteById(existingId);
        Mockito.when(repository.existsById(existingId)).thenReturn(true);
        Mockito.when(repository.existsById(NonexistingId)).thenReturn(false);
    }

    @Test
    public void deleteShouldDoNothingWhenIdExists(){

        Assertions.assertDoesNotThrow(() -> {
            service.delete(existingId);
        });

        //verfica se o metodo deleteById foi chamado nessa ação
        Mockito.verify(repository, Mockito.times(1)).deleteById(existingId);

    }



}
