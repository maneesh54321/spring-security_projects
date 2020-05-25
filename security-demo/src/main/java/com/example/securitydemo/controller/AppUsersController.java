package com.example.securitydemo.controller;

import com.example.securitydemo.service.user.SecurityDemoUserDetailsService;
import com.example.securitydemo.vo.AppUserDetails;
import com.example.securitydemo.vo.SignupUserCredential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class AppUsersController {

    private final SecurityDemoUserDetailsService userDetailsService;

    @Autowired
    public AppUsersController(SecurityDemoUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping
    public List<AppUserDetails> getAllUsers() {
        return userDetailsService.getAllUserDetails();
    }

    @PostMapping
    public void signup(@RequestBody SignupUserCredential signupUserDetails) {
        userDetailsService.signupUser(signupUserDetails);
    }

    @DeleteMapping
    public void deleteUser(@RequestParam String username){
        userDetailsService.deleteUser(username);
    }

    @GetMapping(value = "/roles")
    public Set<String> getAllRoles(){
        return new HashSet<>(Arrays.asList("admin","user"));
    }
}
