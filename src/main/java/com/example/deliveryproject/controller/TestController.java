package com.example.deliveryproject.controller;

import com.example.deliveryproject.dto.OfficeDto;
import com.example.deliveryproject.dto.PostingDto;
import com.example.deliveryproject.dto.PostingEventDto;
import com.example.deliveryproject.dto.UserDto;
import com.example.deliveryproject.entity.Office;
import com.example.deliveryproject.entity.User;
import com.example.deliveryproject.repository.OfficeRepository;
import com.example.deliveryproject.service.OfficeService;
import com.example.deliveryproject.service.PostingEventService;
import com.example.deliveryproject.service.PostingService;
import com.example.deliveryproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TestController {
    private PostingService postingService;
    private UserService userService;
    private PostingEventService postingEventService;
    private OfficeService officeService;

    @Autowired
    OfficeRepository officeRepository;

    public TestController(PostingService postingService, UserService userService, PostingEventService postingEventService, OfficeService officeService) {
        this.postingService = postingService;
        this.userService = userService;
        this.postingEventService = postingEventService;
        this.officeService = officeService;
    }

    private static String getValueFromJsonString(String jsonString, String fieldName) {
        int startIndex = jsonString.indexOf(fieldName) + fieldName.length() + 3;
        int endIndex = jsonString.indexOf("\"", startIndex);

        return jsonString.substring(startIndex, endIndex);
    }

    @GetMapping("/getPostingDto/{postingNumber}")
    public ResponseEntity<PostingDto> getPostingDtoByPostingNumber(@PathVariable String postingNumber) {
        System.out.println(postingNumber);
        PostingDto postingDto = postingService.findPostingDTOByPostingNumber(postingNumber);
        return ResponseEntity.ok(postingDto);
    }

    @GetMapping("/getUserDto/{userId}")
    public ResponseEntity<UserDto> getUserDtoByUserId2(@PathVariable String userId) {
        Long userID = Long.parseLong(userId);
        System.out.println(userId);
        UserDto userDto = userService.findDtoById(userID);
        System.out.println(userId);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/getPostingDtoList")
    public List<PostingDto> getPostingDtoList() {
        List<PostingDto> postingDtoList = postingService.findAllPostings();
        return postingDtoList;
    }

    @GetMapping("/getUserDtoList")
    public List<UserDto> getUserDtoList() {
        List<UserDto> userDtoList = userService.findAllUsers();
        return userDtoList;
    }

    @PostMapping("/users/edit/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute("user") User user, Model model) {

        // get user from database by id
        User existingUser = userService.getUserById(id);
        existingUser.setId(id);
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        // save updated user object
        userService.updateUser(existingUser);
        return "redirect:/users";
    }

    @PostMapping("/users2/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @RequestBody String body) {
        System.out.println(body);
        String firstName = getValueFromJsonString(body, "firstName");
        String lastName = getValueFromJsonString(body, "lastName");
        String email = getValueFromJsonString(body, "email");
        User existingUser = userService.getUserById(id);
        existingUser.setId(id);
        System.out.println(firstName + " " + lastName);
        existingUser.setName(firstName + " " + lastName);
        existingUser.setEmail(email);
        // save updated user object
        userService.updateUser(existingUser);
        return ResponseEntity.ok().build();
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

