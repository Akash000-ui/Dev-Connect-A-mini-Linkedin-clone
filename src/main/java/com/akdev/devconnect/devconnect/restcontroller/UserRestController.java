package com.akdev.devconnect.devconnect.restcontroller;


import com.akdev.devconnect.devconnect.dto.LoginRequest;
import com.akdev.devconnect.devconnect.dto.PostWithCommentsDisLikesAndLikesDTO;
import com.akdev.devconnect.devconnect.model.UsersModel;
import com.akdev.devconnect.devconnect.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
public class UserRestController {

    @Autowired
    private UserService userService;

    @RequestMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody UsersModel user) {
        userService.addUser(user);

        Map<String , Object> response = new HashMap<>();
        response.put( "status" , HttpStatus.CREATED.value());
        response.put("message", "User registered successfully");
        response.put("timestamp", LocalDateTime.now());

        return new ResponseEntity<>(response , HttpStatus.CREATED);
    }

    @RequestMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody LoginRequest loginRequest, HttpSession session) {

        userService.isValid(loginRequest , session);
        Map<String , Object> response = new HashMap<>();
        response.put( "status" , HttpStatus.OK.value());
        response.put("message", "User logged in successfully");
        response.put("timestamp", LocalDateTime.now());

        return new ResponseEntity<>(response , HttpStatus.OK);
    }

    @RequestMapping("/posts")
    public List<PostWithCommentsDisLikesAndLikesDTO> getPosts() {
        return userService.getPosts();
    }


}
