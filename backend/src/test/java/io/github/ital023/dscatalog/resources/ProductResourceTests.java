package io.github.ital023.dscatalog.resources;

import io.github.ital023.dscatalog.dto.ProductDTO;
import io.github.ital023.dscatalog.services.ProductService;
import io.github.ital023.dscatalog.tests.Factory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductResource.class)
public class ProductResourceTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;


    private ProductDTO productDTO;
    private PageImpl<ProductDTO> page;

    @BeforeEach
    void setUp() {
        productDTO = Factory.productDTO();
        page = new PageImpl<>(List.of(productDTO));

        when(productService.findAllPaged(ArgumentMatchers.any())).thenReturn(page);
    }

    @Test
    void findAllShouldReturnPage() throws Exception{
        mockMvc.perform(get("/products")).andExpect(status().isOk());


    }

}
