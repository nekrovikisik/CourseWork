package com.example.deliveryproject.controller;

import com.example.deliveryproject.dto.OfficeDto;
import com.example.deliveryproject.dto.PostingDto;
import com.example.deliveryproject.dto.PostingEventDto;
import com.example.deliveryproject.repository.DeliveryTariffRepository;
import com.example.deliveryproject.repository.OfficeRepository;
import com.example.deliveryproject.service.OfficeService;
import com.example.deliveryproject.service.PostingEventService;
import com.example.deliveryproject.service.PostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class PostingController {

    private PostingService postingService;
    private PostingEventService postingEventService;
    private OfficeService officeService;


    @Autowired
    private OfficeRepository officeRepository;

    @Autowired
    private DeliveryTariffRepository deliveryTariffRepository;

    public PostingController(PostingService postingService, PostingEventService postingEventService, OfficeService officeService) {
        this.postingService = postingService;
        this.postingEventService = postingEventService;
        this.officeService = officeService;
    }

    @GetMapping("/postings/{postingNumber}")
    public String deletePosting(@PathVariable String postingNumber) {
        postingService.deletePostingByPostingNumber(postingNumber);
        return "redirect:/postings";
    }

    @GetMapping("/postings/show/{postingNumber}")
    public ModelAndView showPosting(@PathVariable String postingNumber) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("show_posting");
        return modelAndView;
    }

    @GetMapping("/postings")
    public ModelAndView showListPostings(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("postings");
        return modelAndView;
    }

    @DeleteMapping("/postings/delete/{postingNumber}")
    public ResponseEntity<String> deletePosting2(@PathVariable String postingNumber){
        boolean isRemoved = postingService.deletePostingByPostingNumber(postingNumber);
        if (!isRemoved) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(postingNumber, HttpStatus.OK);
    }

    // -------------------
    @GetMapping("/getPostingDto/{postingNumber}")
    public ResponseEntity<PostingDto> getPostingDtoByPostingNumber(@PathVariable String postingNumber) {
        System.out.println(postingNumber);
        PostingDto postingDto = postingService.findPostingDTOByPostingNumber(postingNumber);
        return ResponseEntity.ok(postingDto);
    }
    @GetMapping("/getPostingDtoList")
    public List<PostingDto> getPostingDtoList() {
        List<PostingDto> postingDtoList = postingService.findAllPostings();
        return postingDtoList;
    }
    @GetMapping("/getPostingEvents/{postingNumber}")
    public List<PostingEventDto> getUserDtoList(@PathVariable String postingNumber) {
        List<PostingEventDto> postingEventDtos = postingEventService.findEventsDtoByPostingNumber(postingNumber);
        return postingEventDtos;
    }

    @GetMapping("/getPostingsBySenderId/{senderId}")
    public List<PostingDto> getPostingsBySenderId(@PathVariable Long senderId) {
        List<PostingDto> postingDtos = postingService.findPostingsBySenderID(senderId);
        return postingDtos;
    }

    @GetMapping("/getPostingsByReceiverId/{receiverId}")
    public List<PostingDto> getPostingsByReceiverId(@PathVariable Long receiverId) {
        List<PostingDto> postingDtos = postingService.findPostingsByReceiverID(receiverId);
        return postingDtos;
    }
    @GetMapping("/getOfficesByRegion/{region}")
    public List<OfficeDto> getOfficesByRegion(@PathVariable String region) {
        List<OfficeDto> officeDtoList;
        if (region.equals(new String("Все регионы России"))) {
            officeDtoList = officeService.findAll();
        } else {
            officeDtoList = officeService.findOfficeDtoByRegion(region);
        }
        return officeDtoList;
    }

    @GetMapping("/getRegionList")
    public List<String> getRegions() {
        System.out.println("Ищу регионы");
        return officeRepository.findAllRegions();
    }



}
