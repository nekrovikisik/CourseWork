package com.example.deliveryproject.controller;

import com.example.deliveryproject.dto.PostingDto;
import com.example.deliveryproject.dto.UserDto;
import com.example.deliveryproject.entity.Posting;
import com.example.deliveryproject.entity.PostingEvent;
import com.example.deliveryproject.entity.User;
import com.example.deliveryproject.repository.*;
import com.example.deliveryproject.service.PostingService;
import com.example.deliveryproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.example.deliveryproject.controller.UserController.getValueFromJsonString;

@RestController
public class CreatePostingController {
    private UserService userService;
    private PostingService postingService;
    @Autowired
    private DeliveryTariffRepository deliveryTariffRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OfficeRepository officeRepository;
    @Autowired
    private PostingEventRepository postingEventRepository;

    public CreatePostingController(UserService userService, PostingService postingService) {
        this.userService = userService;
        this.postingService = postingService;
    }

    @GetMapping("/getTariffList")
    public List<String> getTariffList() {
        List<String> deliveryTariffList = deliveryTariffRepository.findAllTariffNames();
        System.out.println(deliveryTariffList);
        return deliveryTariffList;
    }

    @RequestMapping("/create-posting")
    public ModelAndView listRegisteredUsers(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("create-posting");
        return modelAndView;
    }
    @RequestMapping("/offices")
    public ModelAndView displayOfficesOnMap(){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("offices");
        return modelAndView;
    }

    @GetMapping("/users/checkUserExists/{email}")
    public ResponseEntity<String> deletePosting2(@PathVariable String email){
        System.out.println(email);
        if (userRepository.findByEmail(email)==null){
            System.out.println("404");
            return new ResponseEntity<>("bad email", new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
        System.out.println("Такой пользователь существует");
        return new ResponseEntity<>("success", new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/create-posting")
    public ResponseEntity<?> createPosting(@RequestBody String body) {
        System.out.println(body);
        String tariffName = getValueFromJsonString(body, "tariffName");
        System.out.println(tariffName);
        String senderEmail = getValueFromJsonString(body, "senderEmail");
        System.out.println(senderEmail);
        String receiverEmail = getValueFromJsonString(body, "receiverEmail");
        System.out.println(receiverEmail);
        String officeFrom = getValueFromJsonString(body, "officeFrom");
        System.out.println(officeFrom);
        String officeTo = getValueFromJsonString(body, "officeTo");
        System.out.println(officeTo);

        PostingDto newPostingDto = new PostingDto();
        newPostingDto.setTariffName(tariffName);
        newPostingDto.setSenderEmail(senderEmail);
        newPostingDto.setReceiverEmail(receiverEmail);
        newPostingDto.setOfficeFromName(officeFrom);
        newPostingDto.setOfficeToName(officeTo);

        postingService.savePosting(newPostingDto);

        return ResponseEntity.ok().build();
    }

}
