package com.akdev.devconnect.devconnect.restcontroller;

import com.akdev.devconnect.devconnect.dto.UserUpdateReqDTO;
import com.akdev.devconnect.devconnect.model.UsersModel;
import com.akdev.devconnect.devconnect.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
public class UpdateUserRestController {


    @Autowired
    private UserRepo userRepo;

    @PutMapping(value = "/update/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<Object> updateUser(
            @PathVariable Long id,
            @RequestPart("user") UserUpdateReqDTO userUpdateReqDTO,
            @RequestPart(value = "image", required = false) MultipartFile image) {

        Optional<UsersModel> userOptional = userRepo.findById(id);
        if (!userOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        UsersModel user = userOptional.get();
        user.setName(userUpdateReqDTO.getName());
        user.setEmail(userUpdateReqDTO.getEmail());
        user.setBio(userUpdateReqDTO.getBio());
        user.setSkills(userUpdateReqDTO.getSkills());

        if (image != null && !image.isEmpty()) {
            String uploadDir = "D:\\Dev-Connect-A-mini-Linkedin-clone\\uploads";
            String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
            File dir = new File(uploadDir);

            if (!dir.exists()) {
                dir.mkdirs();
            }

            try {
                String filePath = uploadDir + File.separator + fileName;
                image.transferTo(new File(filePath));
                user.setProfileImageUrl("http://localhost:2525/uploads/" + fileName);
            } catch (Exception e) {
                Map<String , Object> response = new HashMap<>();
                response.put("message", "Error uploading image: " + e.getMessage());
                response.put("status", 200);
                response.put("user", user);
                return ResponseEntity.ok(response);
            }
        }

        userRepo.save(user);
        Map<String , Object> response = new HashMap<>();
        response.put("message", "User updated successfully");
        response.put("status", 200);
        return ResponseEntity.ok(response);

    }

}
