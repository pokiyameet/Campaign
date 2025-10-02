package com.example.Campaign.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductPriceHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long productId;
    private double oldPrice;
    private double newPrice;
    private String reason;
    private LocalDateTime changedAt = LocalDateTime.now();
}

