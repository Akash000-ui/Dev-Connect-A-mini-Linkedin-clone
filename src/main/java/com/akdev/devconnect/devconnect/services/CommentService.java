package com.akdev.devconnect.devconnect.services;

import com.akdev.devconnect.devconnect.exception.CommentException;
import com.akdev.devconnect.devconnect.model.Comments;
import com.akdev.devconnect.devconnect.repositories.CommentsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private CommentsRepo commentsRepo;

    public void addComment(Comments comments){
        try {
            commentsRepo.save(comments);
        } catch (Exception e) {
            throw new CommentException(e.getMessage());
        }
    }
}
