package com.example.UserMS.controllers;



import com.example.UserMS.ApiResponse;
import com.example.UserMS.User;
import com.example.UserMS.UserRepo;
import com.example.UserMS.UserService;
import com.example.UserMS.exceptions.UserNotFoundException;
import com.example.UserMS.requests.LogInRequest;
import com.example.UserMS.requests.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepo userRepo;

    @PostMapping("/register")
    public ApiResponse<User> registerUser(@RequestBody RegisterRequest request)
    {
        if(userService.verify(request))
        {
            User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(request.getPassword());
            user.setEmail(request.getEmail());
            user.setRole(request.getRole());
            User savedUser = userService.saveNewUser(user);
            return ApiResponse.onSuccess("User registered!!!",
                    savedUser);
        }
        return null;
    }

    @PostMapping("/log-in")
    public ApiResponse<String>logInUser(@RequestParam String email,
                                        @RequestParam String password)
    {
        String token = null;
        LogInRequest req = new LogInRequest();
        req.setEmail(email);
        req.setPassword(password);
        token = userService.login(req);
        return ApiResponse.onSuccess("Successfully logged in", token);
    }




    public User getUser()
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        return userRepo.findByUsername(userDetails.getUsername()).
                orElseThrow(() -> new UserNotFoundException("Couldn't find user"));
    }


}
