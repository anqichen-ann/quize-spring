package com.list.api.Controller;

import com.list.api.domain.Product;
import com.list.api.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.list.api.repository.ProductRepository;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@Validated
public class ProductController {
    @Autowired
    ProductRepository productRepository;

    @GetMapping("/product")
    public ResponseEntity<List<Product>> shouldGetAllProduct(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control","no-cache");
        List<Product> products =
                productRepository.findAll().stream()
                        .map(
                                item ->
                                        Product.builder()
                                                .price(item.getPrice())
                                                .productName(item.getProductName())
                                                .url(item.getUrl())
                                                .build())
                        .collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }

    @PostMapping("/product")
    public ResponseEntity addProduct (@RequestBody @Valid Product product){
        ProductDto productDto = ProductDto.builder()
                .ProductName(product.getProductName())
                .price(product.getPrice())
                .url(product.getUrl())
                .build();
        productRepository.save(productDto);
        return ResponseEntity.created(null).build();
    }

}
