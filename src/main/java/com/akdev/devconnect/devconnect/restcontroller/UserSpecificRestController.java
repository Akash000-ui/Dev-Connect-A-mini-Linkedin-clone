package com.akdev.devconnect.devconnect.restcontroller;

import com.akdev.devconnect.devconnect.services.UserService;
import com.akdev.devconnect.devconnect.services.UserSpecificService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/getUserDetails")
public class UserSpecificRestController {

    @Autowired
    private UserSpecificService userSpecificService;
    @GetMapping("user/{id}")
    public ResponseEntity<Object> getUserData(@PathVariable(name = "id") Long id){
       return userSpecificService.getUserData(id);
    }

    @GetMapping("profile/{id}")
    public ResponseEntity<Object> getUserProfile(@PathVariable(name = "id") Long id){
        return userSpecificService.getUserDataProfile(id);
    }
}
