package com.akdev.devconnect.devconnect.repositories;

import com.akdev.devconnect.devconnect.model.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostsRepo extends JpaRepository<Posts, Long> {
    // Additional query methods can be defined here if needed
}
