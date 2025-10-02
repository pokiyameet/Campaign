package com.example.Campaign.controller;

import com.example.Campaign.model.ProductPriceHistory;
import com.example.Campaign.service.ProductHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductHistoryController {

    @Autowired
    ProductHistoryService historyService;

    @GetMapping("/{id}/history")
    public List<ProductPriceHistory> getPriceHistory(@PathVariable long id) {
        return historyService.getHistory(id);
    }
}

