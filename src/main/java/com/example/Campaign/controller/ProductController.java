package com.example.Campaign.controller;

import com.example.Campaign.dto.ProductDTO;
import com.example.Campaign.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    ProductService productService;

    @PostMapping
    public ProductDTO create(@RequestBody ProductDTO dto) {
        return productService.createProduct(dto);
    }

    @GetMapping("/{id}")
    public ProductDTO getById(@PathVariable long id) {
        return productService.getProductById(id);
    }

    @GetMapping
    public Page<ProductDTO> getAll(@RequestParam int page,
                                   @RequestParam int size) {
        return productService.getAllProducts(page, size);
    }
}
