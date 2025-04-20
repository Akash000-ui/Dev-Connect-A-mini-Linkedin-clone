package com.akdev.devconnect.devconnect.repositories;

import com.akdev.devconnect.devconnect.model.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikesRepo extends JpaRepository<Likes, Long> {

    default long countLikesByPostId(Long postId) {
        return findAll().stream()
                .filter(like -> like.getPost_like().getId().equals(postId))
                .count();
    }
}
