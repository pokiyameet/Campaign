package com.example.Campaign.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    private long id;

    private String title;
    private double mrp;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private double currentPrice;

    private double discount;
    private int inventoryCount;
}
