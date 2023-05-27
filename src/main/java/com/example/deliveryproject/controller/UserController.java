package com.example.deliveryproject.controller;

import com.example.deliveryproject.dto.UserDto;
import com.example.deliveryproject.entity.User;
import com.example.deliveryproject.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public static String getValueFromJsonString(String jsonString, String fieldName) {
        int startIndex = jsonString.indexOf(fieldName) + fieldName.length() + 3;
        int endIndex = jsonString.indexOf("\"", startIndex);

        return jsonString.substring(startIndex, endIndex);
    }

    @RequestMapping("/users")
    public ModelAndView listRegisteredUsers() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("users");
        return modelAndView;
    }

    @GetMapping("/users/show/{id}")
    public ModelAndView showUser(@PathVariable Long id) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("show_user");
        return modelAndView;
    }


    @GetMapping("/users/edit/{id}")
    public ModelAndView editUserForm(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("edit_user");
        return modelAndView;
    }

    @GetMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return "redirect:/users";
    }

    @DeleteMapping("/users/delete/{userID}")
    public ResponseEntity<Long> deleteUser2(@PathVariable Long userID) {
        boolean isRemoved = userService.deleteUserById(userID);
        if (!isRemoved) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userID, HttpStatus.OK);
    }

    @GetMapping("/getUserDto/{userId}")
    public ResponseEntity<UserDto> getUserDtoByUserId2(@PathVariable String userId) {
        Long userID = Long.parseLong(userId);
        UserDto userDto = userService.findDtoById(userID);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/getUserDtoList")
    public List<UserDto> getUserDtoList() {
        List<UserDto> userDtoList = userService.findAllUsers();
        return userDtoList;
    }

    @PostMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @RequestBody String body) {
        String firstName = getValueFromJsonString(body, "firstName");
        String lastName = getValueFromJsonString(body, "lastName");
        String email = getValueFromJsonString(body, "email");
        User existingUser = userService.getUserById(id);
        existingUser.setId(id);
        existingUser.setName(firstName + " " + lastName);
        existingUser.setEmail(email);
        userService.updateUser(existingUser);
        return ResponseEntity.ok().build();
    }

}
