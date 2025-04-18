package com.akdev.devconnect.devconnect.repositories;

import com.akdev.devconnect.devconnect.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsRepo extends JpaRepository<Comments, Long> {

}
