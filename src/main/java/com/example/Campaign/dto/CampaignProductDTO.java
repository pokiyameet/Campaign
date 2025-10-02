package com.example.Campaign.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CampaignProductDTO {
    private long id;
    private long productId;
    private double discount;
}