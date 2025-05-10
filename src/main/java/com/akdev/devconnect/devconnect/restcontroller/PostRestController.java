package com.akdev.devconnect.devconnect.restcontroller;

import com.akdev.devconnect.devconnect.model.Posts;
import com.akdev.devconnect.devconnect.repositories.PostsRepo;
import com.akdev.devconnect.devconnect.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequestMapping("/api/v1/posts")
public class PostRestController {

    @Autowired
    private PostService postService;

    @Autowired
    private PostsRepo postsRepo;

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

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updatePost(@PathVariable Long id,
                                             @RequestBody Posts post) {
        Optional<Posts> existingPostOptional = postsRepo.findById(id);
        if (!existingPostOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Posts existingPost = existingPostOptional.get();
        existingPost.setTitle(post.getTitle());
        existingPost.setContent(post.getContent());
        existingPost.setImageUrl(post.getImageUrl());
        existingPost.setAuthorName(post.getAuthorName());
        postsRepo.save(existingPost);

        Map<String, Object> response = new HashMap<>();
        response.put("status", 200);
        response.put("message", "Post updated successfully");
        response.put("timestamp", System.currentTimeMillis());
        response.put("post", existingPost);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deletePost(@PathVariable Long id) {
        Optional<Posts> postOptional = postsRepo.findById(id);
        if (!postOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        postsRepo.delete(postOptional.get());

        Map<String, Object> response = new HashMap<>();
        response.put("status", 200);
        response.put("message", "Post deleted successfully");
        response.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.ok(response);
    }
}
