package com.akdev.devconnect.devconnect.repositories;

import com.akdev.devconnect.devconnect.model.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikesRepo extends JpaRepository<Likes, Long> {

    default Long countLikesByPostId(Long postId) {
        return findAll().stream()
                .filter(like -> like.getPost_like() != null && like.getPost_like().getId().equals(postId))
                .count();
    }


    default boolean existsByUser_likeAndPost_like(Long userLikeId, Long postLikeId) {
        return findAll().stream()
                .anyMatch(like ->
                        like.getUser_like() != null && like.getPost_like() != null &&
                                like.getUser_like().getId().equals(userLikeId) &&
                                like.getPost_like().getId().equals(postLikeId));
    }

    default void deleteByUser_likeAndPost_like(Long userLikeId, Long postLikeId) {
        Likes like = findAll().stream()
                .filter(likes ->
                        likes.getUser_like() != null && likes.getPost_like() != null &&
                                likes.getUser_like().getId().equals(userLikeId) &&
                                likes.getPost_like().getId().equals(postLikeId))
                .findFirst()
                .orElse(null);

        if (like != null) {
            delete(like);
        }
    }

}
