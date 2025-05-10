package com.akdev.devconnect.devconnect.services;

import com.akdev.devconnect.devconnect.exception.PostsException;
import com.akdev.devconnect.devconnect.model.Posts;
import com.akdev.devconnect.devconnect.repositories.PostsRepo;
import com.akdev.devconnect.devconnect.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class PostService {

    @Autowired
    private PostsRepo postsRepo;

    @Autowired
    private UserRepo userRepo;

    public void addPost(
            String title,
            String content,
            String authorName,
            String authorId,
            MultipartFile image
    ) {
        String uploadDir = "D:\\Dev-Connect-A-mini-Linkedin-clone\\uploads"; // Directory to save uploaded images
        // Generate a unique filename to avoid conflicts
        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
        File dir = new File(uploadDir);

        // Create the directory if it doesn't exist
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try {
            // Create the full path for the file to be saved
            String filePath = uploadDir + File.separator + fileName;
            System.out.println("File path: " + filePath);
            File destinationFile = new File(filePath);

            // Save the uploaded image to the file
            image.transferTo(destinationFile);

            // Create the post object
            Posts newPost = new Posts();
            newPost.setTitle(title);
            newPost.setContent(content);
            // Set the image URL to be accessible via HTTP
            newPost.setImageUrl("http://localhost:2525/uploads/" + fileName);
            newPost.setAuthorName(authorName);
            Long id = Long.parseLong(authorId);
            newPost.setAuthor(userRepo.findById(id).orElseThrow(() -> new PostsException("User not found")));

            // Save the post to the database
            postsRepo.save(newPost);

        } catch (IOException e) {
            throw new PostsException("Unable to upload image: " + e.getMessage());
        }
    }
}
