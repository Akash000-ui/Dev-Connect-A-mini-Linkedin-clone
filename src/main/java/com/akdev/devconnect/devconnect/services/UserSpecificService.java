package com.akdev.devconnect.devconnect.services;

import com.akdev.devconnect.devconnect.dto.UserSpecificDataDTO;
import com.akdev.devconnect.devconnect.exception.UserException;
import com.akdev.devconnect.devconnect.model.UsersModel;
import com.akdev.devconnect.devconnect.repositories.PostsRepo;
import com.akdev.devconnect.devconnect.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Optional;

@Service
public class UserSpecificService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PostsRepo postsRepo;

    @Autowired
    private UserService userService;

    public ResponseEntity<Object> getUserData(Long id){
        Optional<UsersModel> user = userRepo.findById(id);
        if (user.isEmpty())  {
            return ResponseEntity.status(404).body("User not found");
        }
        HashMap<String , Object> response = null;
        try {
            UserSpecificDataDTO userSpecificDataDTO = new UserSpecificDataDTO();
            userSpecificDataDTO.setId(user.get().getId());
            userSpecificDataDTO.setName(user.get().getName());
            userSpecificDataDTO.setEmail(user.get().getEmail());
            userSpecificDataDTO.setProfileImageUrl(user.get().getProfileImageUrl());
            userSpecificDataDTO.setBio(user.get().getBio());
            userSpecificDataDTO.setSkills(user.get().getSkills());

            userSpecificDataDTO.setPosts(userService.getPostByUserId(id));

            response = new HashMap<>();
            response.put("user", userSpecificDataDTO);
            response.put("message", "User data fetched successfully");
            response.put("status", 200);
        } catch (Exception e) {
            throw new UserException("Error fetching user data: " + e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Object> getUserDataProfile(Long id){
        Optional<UsersModel> user = userRepo.findById(id);
        if (user.isEmpty())  {
            return ResponseEntity.status(404).body("User not found");
        }
        HashMap<String , Object> response = null;
        try {
            UserSpecificDataDTO userSpecificDataDTO = new UserSpecificDataDTO();
            userSpecificDataDTO.setId(user.get().getId());
            userSpecificDataDTO.setName(user.get().getName());
            userSpecificDataDTO.setEmail(user.get().getEmail());
            userSpecificDataDTO.setProfileImageUrl(user.get().getProfileImageUrl());
            userSpecificDataDTO.setBio(user.get().getBio());
            userSpecificDataDTO.setSkills(user.get().getSkills());

            response = new HashMap<>();
            response.put("data", userSpecificDataDTO);
            response.put("message", "User data fetched successfully");
            response.put("status", 200);
        } catch (Exception e) {
            throw new UserException("Error fetching user data: " + e.getMessage());
        }
        return ResponseEntity.ok(response);
    }
}
