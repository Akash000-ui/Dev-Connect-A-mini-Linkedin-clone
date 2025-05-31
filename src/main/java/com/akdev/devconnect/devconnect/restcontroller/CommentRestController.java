package com.akdev.devconnect.devconnect.restcontroller;

import com.akdev.devconnect.devconnect.model.Comments;
import com.akdev.devconnect.devconnect.model.Posts;
import com.akdev.devconnect.devconnect.model.UsersModel;
import com.akdev.devconnect.devconnect.repositories.PostsRepo;
import com.akdev.devconnect.devconnect.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentRestController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostsRepo postsRepo;

    @Autowired
    private NotificationRestController notificationRestController;

    @RequestMapping("/add")
    public ResponseEntity<Object> addComments(@RequestBody Comments comments){
        commentService.addComment(comments);
        //send notification to the user
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        System.out.println("Sending notification to user: " + comments);
        Long postId = comments.getPost_comment_id();
        Optional<Posts> commentedPost = postsRepo.findById(postId);
        UsersModel postAuthor = null;
        if (commentedPost.isPresent()) {
            Posts post = commentedPost.get();
            postAuthor = post.getAuthor();
        }else {
            throw new RuntimeException("Post not found with ID: " + postId);
        }
        notificationRestController.sendMessage(comments.getComment_user_id() , postAuthor.getId() , postId , "New comment on your post");
        System.out.println("Notification sent to user: " + postAuthor.getId());
        Map<String , Object> response = Map.of(
                "status", 201,
                "message", "Comment added successfully",
                "timestamp", System.currentTimeMillis()
        );
        return ResponseEntity.status(201).body(response);
    }
}
