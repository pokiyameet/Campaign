package com.example.Campaign.service;

import com.example.Campaign.model.ProductPriceHistory;
import com.example.Campaign.repository.ProductPriceHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductHistoryService {

    @Autowired
    private ProductPriceHistoryRepository historyRepository;

    public List<ProductPriceHistory> getHistory(long productId) {
        return historyRepository.findByProductIdOrderByChangedAtDesc(productId);
    }
}

