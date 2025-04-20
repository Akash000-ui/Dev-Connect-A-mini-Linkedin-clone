package com.akdev.devconnect.devconnect.restcontroller;


import com.akdev.devconnect.devconnect.model.Posts;
import com.akdev.devconnect.devconnect.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/posts")
public class PostRestController {

    @Autowired
    private PostService postService;

    @RequestMapping("/add")
    public ResponseEntity<Object> addPost(@RequestBody Posts post) {

        postService.addPost(post);
        Map<String , Object> response = new HashMap<>();

        response.put("status", 201);
        response.put("message", "Post added successfully");
        response.put("timestamp", System.currentTimeMillis());
        response.put("post", post);

        return ResponseEntity.status(201).body(response);
    }
}
