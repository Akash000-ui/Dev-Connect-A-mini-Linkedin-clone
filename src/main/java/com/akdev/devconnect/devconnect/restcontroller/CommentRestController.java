package com.akdev.devconnect.devconnect.restcontroller;

import com.akdev.devconnect.devconnect.model.Comments;
import com.akdev.devconnect.devconnect.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentRestController {

    @Autowired
    private CommentService commentService;

    @RequestMapping("/add")
    public ResponseEntity<Object> addComments(@RequestBody Comments comments){
        commentService.addComment(comments);
        Map<String , Object> response = Map.of(
                "status", 201,
                "message", "Comment added successfully",
                "timestamp", System.currentTimeMillis()
        );
        return ResponseEntity.status(201).body(response);
    }
}
