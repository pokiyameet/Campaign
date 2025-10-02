package com.example.Campaign.service;

import com.example.Campaign.dto.CampaignDTO;
import com.example.Campaign.dto.CampaignProductDTO;
import com.example.Campaign.model.Campaign;
import com.example.Campaign.model.CampaignProduct;
import com.example.Campaign.repository.CampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CampaignService {

    @Autowired
    CampaignRepository campaignRepository;

    // Create a new Campaign
    public CampaignDTO createCampaign(CampaignDTO dto) {
        Campaign campaign = new Campaign();
        campaign.setTitle(dto.getTitle());
        campaign.setStartDate(dto.getStartDate());
        campaign.setEndDate(dto.getEndDate());

        // Prepare product list
        List<CampaignProduct> productList = new ArrayList<>();

        if (dto.getProducts() != null) {  // avoid NullPointerException
            for (CampaignProductDTO productDto : dto.getProducts()) {
                CampaignProduct campaignProduct = new CampaignProduct();
                campaignProduct.setProductId(productDto.getProductId());
                campaignProduct.setDiscount(productDto.getDiscount());
                campaignProduct.setCampaign(campaign);

                productList.add(campaignProduct);
            }
        }

        campaign.setProducts(productList);

        Campaign saved = campaignRepository.save(campaign);
        return convertToDTO(saved);
    }

    // Get Campaign by ID
    public CampaignDTO getCampaignById(long id) {
        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Campaign not found"));

        return convertToDTO(campaign);
    }

    // Get All Campaigns
    public List<CampaignDTO> getAllCampaigns() {
        List<Campaign> campaigns = campaignRepository.findAll();
        List<CampaignDTO> dtos = new ArrayList<>();

        for (Campaign campaign : campaigns) {
            dtos.add(convertToDTO(campaign));
        }

        return dtos;
    }

    // Convert Entity -> DTO
    private CampaignDTO convertToDTO(Campaign campaign) {
        CampaignDTO dto = new CampaignDTO();
        dto.setId(campaign.getId());
        dto.setTitle(campaign.getTitle());
        dto.setStartDate(campaign.getStartDate());
        dto.setEndDate(campaign.getEndDate());

        List<CampaignProductDTO> productDtos = new ArrayList<>();

        if (campaign.getProducts() != null) {
            for (CampaignProduct cp : campaign.getProducts()) {
                CampaignProductDTO productDto = new CampaignProductDTO();
                productDto.setId(cp.getId());
                productDto.setProductId(cp.getProductId());
                productDto.setDiscount(cp.getDiscount());

                productDtos.add(productDto);
            }
        }

        dto.setProducts(productDtos);
        return dto;
    }
}
