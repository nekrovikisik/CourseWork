package com.example.deliveryproject.controller;

import com.example.deliveryproject.dto.PostingDto;
import com.example.deliveryproject.entity.DeliveryTariff;
import com.example.deliveryproject.entity.Office;
import com.example.deliveryproject.entity.Posting;
import com.example.deliveryproject.repository.DeliveryTariffRepository;
import com.example.deliveryproject.repository.OfficeRepository;
import com.example.deliveryproject.service.PostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Controller
public class PostingController {

    private PostingService postingService;

    @Autowired
    private OfficeRepository officeRepository;

    @Autowired
    private DeliveryTariffRepository deliveryTariffRepository;

    public PostingController(PostingService postingService) {
        this.postingService = postingService;
    }

    @GetMapping("/postings")
    public String listPostings(Model model){
        List<PostingDto> postings = postingService.findAllPostings();
        model.addAttribute("postings", postings);
        return "postings";
    }

    @GetMapping("/postings/edit/{postingNumber}")
    public String editPostingForm(@PathVariable String postingNumber, Model model) {
        List deliveryTariffs = deliveryTariffRepository.findAllTariffNames();
        List regions = officeRepository.findAllRegions();
        List offices = officeRepository.findAll();
        model.addAttribute("posting", postingService.findByPostingNumber(postingNumber));
        model.addAttribute("deliveryTariffs", deliveryTariffs);
        model.addAttribute("regions", regions);
        model.addAttribute("offices", offices);
        return "edit_posting";
    }

    @PostMapping("/postings/{postingNumber}")
    public String updatePosting(@PathVariable String postingNumber,
                             @ModelAttribute("posting") Posting posting,
                             Model model) {

        Posting existingPosting = postingService.findByPostingNumber(postingNumber);

        String newDeliveryTariffName = posting.getDeliveryTariff().getTariffName();
        DeliveryTariff newDeliveryTariff = deliveryTariffRepository.findByTariffName(newDeliveryTariffName);
        existingPosting.setDeliveryTariff(newDeliveryTariff);

        String newOfficeFromName = posting.getOfficeFrom().getFullOfficeName();
        Office newOfficeFrom = officeRepository.findByFullOfficeName(newOfficeFromName);
        existingPosting.setOfficeFrom(newOfficeFrom);

        String newOfficeToName = posting.getOfficeTo().getFullOfficeName();
        Office newOfficeTo = officeRepository.findByFullOfficeName(newOfficeToName);
        existingPosting.setOfficeTo(newOfficeTo);

        postingService.updatePosting(existingPosting);
        return "redirect:/postings";
    }

    @GetMapping("/postings/{postingNumber}")
    public String deletePosting(@PathVariable String postingNumber) {
        postingService.deletePostingByPostingNumber(postingNumber);
        return "redirect:/postings";
    }

    @GetMapping("/postings/show/{postingNumber}")
    public String showPosting(@PathVariable String postingNumber, Model model) {
        PostingDto posting = postingService.findPostingDTOByPostingNumber(postingNumber);
        model.addAttribute("posting", posting);
        return "show_posting";
    }

    @GetMapping("/postings/show2/{postingNumber}")
    public String test_page(@PathVariable String postingNumber) {
        return "show_posting2";
    }

    @GetMapping("/postings2")
    public String test_postings(){
        return "postings2";
    }


}
