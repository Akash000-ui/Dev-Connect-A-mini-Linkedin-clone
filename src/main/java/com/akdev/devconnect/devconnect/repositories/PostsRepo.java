package com.akdev.devconnect.devconnect.repositories;

import com.akdev.devconnect.devconnect.model.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostsRepo extends JpaRepository<Posts, Long> {

    // Custom query method to find posts by title containing a specific term, ignoring case
    List<Posts> findByTitleContainingIgnoreCase(String term);
    // Additional query methods can be defined here if needed
}
