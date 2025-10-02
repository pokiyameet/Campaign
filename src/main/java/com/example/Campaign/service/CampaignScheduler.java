package com.example.Campaign.service;

import com.example.Campaign.model.Campaign;
import com.example.Campaign.model.CampaignProduct;
import com.example.Campaign.model.CampaignStatus;
import com.example.Campaign.model.Product;
import com.example.Campaign.model.ProductPriceHistory;
import com.example.Campaign.repository.CampaignRepository;
import com.example.Campaign.repository.ProductPriceHistoryRepository;
import com.example.Campaign.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class CampaignScheduler {

    @Autowired
    CampaignRepository campaignRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductPriceHistoryRepository historyRepository;

    // This method runs every minute (because of cron = "0 * * * * ?")
    @Scheduled(cron = "0 * * * * ?")
    public void updateCampaignStatuses() {
        LocalDate today = LocalDate.now();
        List<Campaign> campaigns = campaignRepository.findAll();

        for (Campaign campaign : campaigns) {
            // If campaign start date is in future
            if (campaign.getStartDate().isAfter(today)) {
                campaign.setStatus(CampaignStatus.UPCOMING);
            }
            // If campaign is running today
            else if (!campaign.getStartDate().isAfter(today) && !campaign.getEndDate().isBefore(today)) {
                if (campaign.getStatus() != CampaignStatus.ACTIVE) {
                    applyDiscounts(campaign);
                }
                campaign.setStatus(CampaignStatus.ACTIVE);
            }
            // If campaign has ended
            else if (campaign.getEndDate().isBefore(today)) {
                if (campaign.getStatus() != CampaignStatus.ENDED) {
                    revertDiscounts(campaign);
                }
                campaign.setStatus(CampaignStatus.ENDED);
            }
        }

        campaignRepository.saveAll(campaigns);
    }

    // Method to apply discounts when campaign starts
    private void applyDiscounts(Campaign campaign) {
        List<CampaignProduct> campaignProducts = campaign.getProducts();

        for (CampaignProduct cp : campaignProducts) {
            Product product = productRepository.findById(cp.getProductId()).orElse(null);

            if (product != null) {

                // Save original price if not already saved
                if (product.getOriginalPrice() == null) {
                    product.setOriginalPrice(product.getCurrentPrice());
                }

                double oldPrice = product.getCurrentPrice();
                double discountedPrice = oldPrice - (oldPrice * (cp.getDiscount() / 100.0));

                product.setCurrentPrice(discountedPrice);
                productRepository.save(product);

                // Save history
                ProductPriceHistory history = new ProductPriceHistory();
                history.setProductId(product.getId());
                history.setOldPrice(oldPrice);
                history.setNewPrice(discountedPrice);
                history.setReason("Campaign Started: " + campaign.getTitle());
                historyRepository.save(history);
            }
        }
    }

    // Method to revert discounts when campaign ends
    private void revertDiscounts(Campaign campaign) {
        List<CampaignProduct> campaignProducts = campaign.getProducts();

        for (CampaignProduct cp : campaignProducts) {
            Product product = productRepository.findById(cp.getProductId()).orElse(null);

            if (product != null && product.getOriginalPrice() != null) {
                double oldPrice = product.getCurrentPrice();
                double revertedPrice = product.getOriginalPrice();

                product.setCurrentPrice(revertedPrice);
                product.setOriginalPrice(null); // Clear original price after revert
                productRepository.save(product);

                // Save history
                ProductPriceHistory history = new ProductPriceHistory();
                history.setProductId(product.getId());
                history.setOldPrice(oldPrice);
                history.setNewPrice(revertedPrice);
                history.setReason("Campaign Ended: " + campaign.getTitle());
                historyRepository.save(history);
            }
        }
    }
}
