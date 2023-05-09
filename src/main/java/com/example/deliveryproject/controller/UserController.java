package com.example.deliveryproject.controller;

import com.example.deliveryproject.dto.PostingDto;
import com.example.deliveryproject.dto.UserDto;
import com.example.deliveryproject.entity.User;
import com.example.deliveryproject.repository.PostingRepository;
import com.example.deliveryproject.service.PostingService;
import com.example.deliveryproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {
    private UserService userService;
    private PostingService postingService;
    @Autowired
    private PostingRepository postingRepository;

    public UserController(UserService userService, PostingService postingService) {
        this.userService = userService;
        this.postingService = postingService;
    }

    @GetMapping("/users")
    public String listRegisteredUsers(Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }
    @GetMapping("/users/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "edit_user";
    }

    @PostMapping("/users/{id}")
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

    @GetMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return "redirect:/users";
    }
    @GetMapping("/users/show/{id}")
    public String showUser(@PathVariable Long id, Model model) {
//        List postings = postingRepository.findAllBySenderID(id);
        List<PostingDto> postingsFrom = postingService.findPostingsBySenderID(id);
        List<PostingDto> postingsTo = postingService.findPostingsByReceiverID(id);

        model.addAttribute("user", userService.findDtoById(id));
        model.addAttribute("postingsFrom", postingsFrom);
        model.addAttribute("postingsTo", postingsTo);
        return "show_user";
    }

}
