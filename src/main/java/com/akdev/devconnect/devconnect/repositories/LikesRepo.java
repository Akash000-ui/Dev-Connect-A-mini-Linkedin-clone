package com.akdev.devconnect.devconnect.repositories;

import com.akdev.devconnect.devconnect.model.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikesRepo extends JpaRepository<Likes, Long> {
    // Additional query methods can be defined here if needed
}
