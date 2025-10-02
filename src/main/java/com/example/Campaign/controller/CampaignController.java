package com.example.Campaign.controller;

import com.example.Campaign.dto.CampaignDTO;
import com.example.Campaign.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/campaigns")
public class CampaignController {

    @Autowired
    CampaignService campaignService;

    @PostMapping
    public CampaignDTO createCampaign(@RequestBody CampaignDTO dto) {
        return campaignService.createCampaign(dto);
    }

    @GetMapping("/{id}")
    public CampaignDTO getCampaignById(@PathVariable long id) {
        return campaignService.getCampaignById(id);
    }

    @GetMapping
    public List<CampaignDTO> getAllCampaigns() {
        return campaignService.getAllCampaigns();
    }
}
