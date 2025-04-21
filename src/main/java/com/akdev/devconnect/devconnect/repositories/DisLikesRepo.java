package com.akdev.devconnect.devconnect.repositories;

import com.akdev.devconnect.devconnect.model.DisLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisLikesRepo extends JpaRepository<DisLikes, Long> {

    default Long countDisLikesByPostId(long postId){
        return findAll().stream()
                .filter(disLikes -> disLikes.getPost_dislike().getId() == postId)
                .count();
    }

    default boolean existsByUser_dislikeAndPost_dislike(Long userId, Long postId){
        return findAll().stream()
                .anyMatch(disLikes ->  disLikes.getUser_dislike().getId().equals(userId)
                && disLikes.getPost_dislike().getId().equals(postId));
    }

    default void deleteByUser_dislikeAndPost_dislike(Long userId, Long postId){
        DisLikes disLike = findAll().stream()
                .filter(disLikes -> disLikes.getUser_dislike().getId().equals(userId))
                .filter(disLikes -> disLikes.getPost_dislike().getId().equals(postId))
                .findFirst()
                .orElse(null);
        if (disLike != null) {
            delete(disLike);
        }
    }
}
