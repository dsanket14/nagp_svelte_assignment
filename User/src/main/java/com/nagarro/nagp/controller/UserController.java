package com.nagarro.nagp.controller;

import com.nagarro.nagp.dto.UserProfile;
import com.nagarro.nagp.model.User;
import com.nagarro.nagp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("user")
    public String helloUser() {
        return "Hello User";
    }

    @GetMapping("admin")
    public String helloAdmin() {
        return "Hello Admin";
    }
    @PostMapping("/register")
    private ResponseEntity<User> saveUser(@RequestBody User user){
        userService.registerUser(user);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    private ResponseEntity<User> saveUser(@RequestParam String username){
        return ResponseEntity.ok( userService.getUser(username));
    }

    @GetMapping("/profile")
    private ResponseEntity<UserProfile> getUserProfile(){
        return ResponseEntity.ok( userService.getUserProfile());
    }
}
