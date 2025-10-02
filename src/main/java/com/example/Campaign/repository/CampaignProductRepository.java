package com.example.Campaign.repository;

import com.example.Campaign.model.CampaignProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignProductRepository extends JpaRepository<CampaignProduct, Long> {
}
