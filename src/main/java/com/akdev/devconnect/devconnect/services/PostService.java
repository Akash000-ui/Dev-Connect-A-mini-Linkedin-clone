package com.akdev.devconnect.devconnect.services;


import com.akdev.devconnect.devconnect.exception.PostsException;
import com.akdev.devconnect.devconnect.model.Posts;
import com.akdev.devconnect.devconnect.repositories.PostsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    @Autowired
    private PostsRepo postsRepo;

    public void addPost(Posts post) {

        if (post.getTitle() == null || post.getTitle().isEmpty() || post.getTitle().length() < 3 || post.getTitle().length() > 100
        || post.getTitle().matches(".*\\d.*") || post.getAuthor() == null) {
            throw new PostsException("Invalid post Details");
        }
        postsRepo.save(post);
    }
}
