package com.akdev.devconnect.devconnect.repositories;

import com.akdev.devconnect.devconnect.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentsRepo extends JpaRepository<Comments, Long> {

}
