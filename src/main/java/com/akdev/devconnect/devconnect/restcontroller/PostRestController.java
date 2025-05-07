package com.akdev.devconnect.devconnect.restcontroller;

import com.akdev.devconnect.devconnect.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequestMapping("/api/v1/posts")
public class PostRestController {

    @Autowired
    private PostService postService;

    @PostMapping("/add")
    public ResponseEntity<Object> addPost( @RequestParam("title") String title,
                                           @RequestParam("content") String content,
                                           @RequestParam("authorName") String authorName,
                                           @RequestParam("authorId") String authorId,
                                           @RequestParam("image") MultipartFile image) {

        System.out.printf("Title: %s, Content: %s, AuthorName: %s, AuthorId: %s%n", title, content, authorName, authorId);
        System.out.println(image.getOriginalFilename());
        postService.addPost(title , content , authorName , authorId , image);
        Map<String , Object> response = new HashMap<>();

        response.put("status", 201);
        response.put("message", "Post added successfully");
        response.put("timestamp", System.currentTimeMillis());
//        response.put("post", post);

        return ResponseEntity.status(201).body(response);
    }
}
