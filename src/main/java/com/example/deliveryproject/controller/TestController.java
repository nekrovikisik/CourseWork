package com.example.deliveryproject.controller;
import com.example.deliveryproject.dto.PostingDto;
import com.example.deliveryproject.dto.PostingEventDto;
import com.example.deliveryproject.dto.UserDto;
import com.example.deliveryproject.entity.User;
import com.example.deliveryproject.service.PostingEventService;
import com.example.deliveryproject.service.PostingService;
import com.example.deliveryproject.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TestController {
    private PostingService postingService;
    private UserService userService;
    private PostingEventService postingEventService;

    public TestController(PostingService postingService, UserService userService, PostingEventService postingEventService) {
        this.postingService = postingService;
        this.userService = userService;
        this.postingEventService = postingEventService;
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
    public String updateUser(@PathVariable Long id,
                             @ModelAttribute("user") User user,
                             Model model) {

        // get user from database by id
        User existingUser = userService.getUserById(id);
        existingUser.setId(id);
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        // save updated user object
        userService.updateUser(existingUser);
        return "redirect:/users";
    }

//    @PostMapping("/users2/{id}")
//    public String updateUser2(@PathVariable Long id) {
//
//        // get user from database by id
//        User existingUser = userService.getUserById(id);
//        existingUser.setId(id);
//        // save updated user object
//        userService.updateUser(existingUser);
//        return "redirect:/users";
//    }

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
        // handle the user update logic here
        // you can access the user's firstName and email fields using user.getFirstName() and user.getEmail()
        return ResponseEntity.ok().build();
    }


    @GetMapping("/getPostingEvents/{postingNumber}")
    public List<PostingEventDto> getUserDtoList(@PathVariable String postingNumber) {
        List<PostingEventDto> postingEventDtos = postingEventService.findEventsDtoByPostingNumber(postingNumber);
        return postingEventDtos;
    }

}


//    @GetMapping("/getPostingDtoList")
//    public List<PostingDto> getProductsByCategory(@RequestParam("category") String category) {
//        List<PostingDto> postingDtoList = postingService.findAllPostings();
//        List<PostingDto> postingDtoList = new ArrayList<>();
//        for (PostingDto postingDto : postingDtoList) {
//            postingDtoList.add(new ProductDto(product));
//        }
//        return postingDtoList;
//    }
//}


//curl -X GET 'http://localhost:8080/getWithMultipleRequestParams?postingNumber=1-1' -H 'Content-Type: application/json' -H 'Accept: application/json'



//package com.example.deliveryproject.controller;
//
//import com.example.deliveryproject.dto.PostingDto;
//import com.example.deliveryproject.service.PostingService;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//
//
//@RestController
////@EnableAutoConfiguration
//public class TestController {
//    private PostingService postingService;
//    @GetMapping("/testPostings/{postingNumber}")
//
//    public PostingDto retrievePosting(@PathVariable String postingNumber) {
//        System.out.println("Вызвала очко");
//        PostingDto postingDto = postingService.findPostingDTOByPostingNumber(postingNumber);
//        System.out.println(postingDto.getId());
//
//
////        if (posting)
////            throw new StudentNotFoundException("id-" + id);
//
//        return postingDto;
//    }
//
//}
