package com.akdev.devconnect.devconnect.restcontroller;


import com.akdev.devconnect.devconnect.dto.LoginRequest;
import com.akdev.devconnect.devconnect.dto.OAuthRegisterDTO;
import com.akdev.devconnect.devconnect.dto.PostWithCommentsDisLikesAndLikesDTO;
import com.akdev.devconnect.devconnect.model.UsersModel;
import com.akdev.devconnect.devconnect.services.UserService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
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
        return userService.addUser(user);
    }

    @RequestMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody LoginRequest loginRequest) {

        return userService.isValid(loginRequest);


    }

    @RequestMapping("/posts")
    public List<PostWithCommentsDisLikesAndLikesDTO> getPosts(HttpServletRequest request) {
        Claims claims = (Claims) request.getAttribute("claims");
        return userService.getPosts(claims);
    }


    @RequestMapping("/register/google")
    public ResponseEntity<Object> registerUserWithGoogle(@RequestBody OAuthRegisterDTO oAuthRegisterDTO){

        userService.registerUserWithGoogle(oAuthRegisterDTO);

        Map<String , Object> response = new HashMap<>();
        response.put( "status" , HttpStatus.CREATED.value());
        response.put("message", "User registered successfully");
        response.put("timestamp", LocalDateTime.now());
        return new ResponseEntity<>(response , HttpStatus.CREATED);
    }

    @RequestMapping("/login/google")
    public ResponseEntity<Object> loginUserWithGoogle(@RequestBody Map<String , String> token) {

        return userService.verifyGoogleUser(token);
    }


}
