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
}
