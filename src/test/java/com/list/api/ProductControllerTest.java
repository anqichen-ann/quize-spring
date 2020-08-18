package com.list.api;

import com.list.api.dto.ProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import com.list.api.repository.ProductRepository;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {
    @Autowired private MockMvc mockMvc;
    @Autowired ProductRepository productRepository;
    ProductDto productDto;

    @BeforeEach
    void  setUp() {
        productRepository.deleteAll();
        productDto  = ProductDto.builder()
                .price(5)
                .ProductName("可乐")
                .url(1)
                .build();
        productRepository.save(productDto);
        productDto  = ProductDto.builder()
                .price(4)
                .ProductName("雪碧")
                .url(2)
                .build();
        productRepository.save(productDto);
        productDto  = ProductDto.builder()
                .price(6)
                .ProductName("泡面")
                .url(3)
                .build();
        productRepository.save(productDto);


    }

    @Test
    public  void shouldGetAllProduct() throws Exception {
        mockMvc.perform(get("/product"))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].productName", is("可乐")))
                .andExpect(jsonPath("$[0].price", is(5)))
                .andExpect(jsonPath("$[0].url", is(1)))

                .andExpect(jsonPath("$[1].productName", is("雪碧")))
                .andExpect(jsonPath("$[1].price", is(4)))
                .andExpect(jsonPath("$[1].url", is(2)))

                .andExpect(jsonPath("$[2].productName", is("泡面")))
                .andExpect(jsonPath("$[2].price", is(6)))
                .andExpect(jsonPath("$[2].url", is(3)));
    }

}
