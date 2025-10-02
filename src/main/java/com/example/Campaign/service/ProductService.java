package com.example.Campaign.service;

import com.example.Campaign.dto.ProductDTO;
import com.example.Campaign.model.Product;
import com.example.Campaign.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public ProductDTO createProduct(ProductDTO dto) {
        Product product = new Product();
        product.setTitle(dto.getTitle());
        product.setMrp(dto.getMrp());

//        product.setCurrentPrice(dto.getCurrentPrice());

        product.setDiscount(dto.getDiscount());
        product.setInventoryCount(dto.getInventoryCount());

        double priceAfterDiscount = dto.getMrp() - (dto.getMrp() * dto.getDiscount() / 100);
        product.setCurrentPrice(priceAfterDiscount);
        product.setOriginalPrice(priceAfterDiscount);

        Product saved = productRepository.save(product);

        dto.setId(saved.getId());
        dto.setCurrentPrice(saved.getCurrentPrice());
        return dto;
    }

    public ProductDTO getProductById(long id) {
        Product p = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        ProductDTO dto = new ProductDTO();
        dto.setId(p.getId());
        dto.setTitle(p.getTitle());
        dto.setMrp(p.getMrp());
        dto.setCurrentPrice(p.getCurrentPrice());
        dto.setDiscount(p.getDiscount());
        dto.setInventoryCount(p.getInventoryCount());
        return dto;
    }

    public Page<ProductDTO> getAllProducts(int page, int size) {
        return productRepository.findAll(PageRequest.of(page, size))
                .map(p -> {
                    ProductDTO dto = new ProductDTO();
                    dto.setId(p.getId());
                    dto.setTitle(p.getTitle());
                    dto.setMrp(p.getMrp());
                    dto.setCurrentPrice(p.getCurrentPrice());
                    dto.setDiscount(p.getDiscount());
                    dto.setInventoryCount(p.getInventoryCount());
                    return dto;
                });
    }
}
