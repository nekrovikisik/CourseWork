package com.example.deliveryproject.controller;

import com.example.deliveryproject.dto.PostingDto;
//import com.example.deliveryproject.entity.Posting;
import com.example.deliveryproject.repository.DeliveryTariffRepository;
import com.example.deliveryproject.repository.OfficeRepository;
import com.example.deliveryproject.service.PostingService;
//import com.example.deliveryproject.service.UserService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CreatePostingController {

    private PostingService postingService;

    @Autowired
    private OfficeRepository officeRepository;

    @Autowired
    private DeliveryTariffRepository deliveryTariffRepository;

    public CreatePostingController(PostingService postingService) {
        this.postingService = postingService;
    }

    @GetMapping("/create-posting")
    public String showCreatingPostingForm(Model model){
        PostingDto posting = new PostingDto();
        List deliveryTariffs = deliveryTariffRepository.findAllTariffNames();
        List regions = officeRepository.findAllRegions();
        List offices = officeRepository.findAll();

        model.addAttribute("deliveryTariffs", deliveryTariffs);
        model.addAttribute("regions", regions);
        model.addAttribute("offices", offices);
        model.addAttribute("posting", posting);
        return "create-posting";
    }

    @PostMapping("/create-posting/save")
    public String registration(@Valid @ModelAttribute("posting") PostingDto posting,
                               BindingResult result,
                               Model model){
        if (result.hasErrors()) {
            System.out.println("ошибки" + result.getAllErrors());
            model.addAttribute("posting", posting);
            return "create-posting";
        }
        postingService.savePosting(posting);
        return "redirect:/create-posting?success";
    }


    @GetMapping("/offices")
    public String displayOfficesOnMap(){
        return "offices";
    }


//    @GetMapping("/test")
//    public String testLeaflet(Model model){
////        List offices = officeRepository.findAll();
////        model.addAttribute("offices", offices);
//        return "test";
//    }

}
