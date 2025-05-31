package com.akdev.devconnect.devconnect.services;

import com.akdev.devconnect.devconnect.exception.CommentException;
import com.akdev.devconnect.devconnect.model.Comments;
import com.akdev.devconnect.devconnect.model.Posts;
import com.akdev.devconnect.devconnect.model.UsersModel;
import com.akdev.devconnect.devconnect.repositories.CommentsRepo;
import com.akdev.devconnect.devconnect.repositories.PostsRepo;
import com.akdev.devconnect.devconnect.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private PostsRepo postsRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CommentsRepo commentsRepo;

    public void addComment(Comments comments){
        try {

            Long postId = comments.getPost_comment_id();
            Optional<Posts> commentedPost = postsRepo.findById(postId);
            UsersModel postAuthor = null;
            if (commentedPost.isPresent()) {
                Posts post = commentedPost.get();
                postAuthor = post.getAuthor();
            }else {
                throw new RuntimeException("Post not found with ID: " + postId);
            }
            Optional<UsersModel> commentUser = userRepo.findById(comments.getComment_user_id());
            if (commentUser.isPresent()) {
                UsersModel user = commentUser.get();
                comments.setComment_user(user);
                comments.setPost_comment(commentedPost.get());
            }else {
                throw new RuntimeException("User not found with ID: " + comments.getComment_user_id());
            }
            commentsRepo.save(comments);
        } catch (Exception e) {
            throw new CommentException(e.getMessage());
        }
    }
}
