package com.example.Campaign.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CampaignProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long productId;
    private double discount;

    @ManyToOne
    @JoinColumn(name = "campaign_id")
    private Campaign campaign;
}

