package com.akdev.devconnect.devconnect.repositories;
import com.akdev.devconnect.devconnect.model.UsersModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<UsersModel, Long> {


    List<UsersModel> findByNameContainingIgnoreCase(String searchTerm);
    @Query("SELECT u FROM UsersModel u JOIN u.skills s WHERE LOWER(s) LIKE LOWER(CONCAT('%', :st, '%'))")
    List<UsersModel> searchBySkillIgnoreCase(@Param("st") String st);

    UsersModel findByEmail(String email);
}

