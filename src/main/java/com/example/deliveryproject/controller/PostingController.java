package com.example.deliveryproject.controller;

import com.example.deliveryproject.dto.PostingDto;
import com.example.deliveryproject.entity.DeliveryTariff;
import com.example.deliveryproject.entity.Posting;
import com.example.deliveryproject.repository.DeliveryTariffRepository;
import com.example.deliveryproject.repository.OfficeRepository;
import com.example.deliveryproject.service.PostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

        // get posting from database by postingNumber

        Posting existingPosting = postingService.findByPostingNumber(postingNumber);
        String newDeliveryTariffName = posting.getDeliveryTariff().getTariffName();
        DeliveryTariff newDeliveryTariff = deliveryTariffRepository.findByTariffName(newDeliveryTariffName);

        existingPosting.setDeliveryTariff(newDeliveryTariff);

        existingPosting.setSenderEmail(posting.getSenderEmail());
        existingPosting.setRecieverEmail(posting.getRecieverEmail());
        existingPosting.setOfficeFromID(posting.getOfficeFromID());
        existingPosting.setOfficeToID(posting.getOfficeToID());
        // save updated posting object
        postingService.updatePosting(existingPosting);
        return "redirect:/postings";
    }

    @GetMapping("/postings/{postingNumber}")
    public String deletePosting(@PathVariable String postingNumber) {
        postingService.deletePostingByPostingNumber(postingNumber);
        return "redirect:/postings";
    }

}
