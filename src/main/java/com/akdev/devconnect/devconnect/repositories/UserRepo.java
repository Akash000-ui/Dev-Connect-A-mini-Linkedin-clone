package com.akdev.devconnect.devconnect.repositories;
import com.akdev.devconnect.devconnect.model.UsersModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UsersModel, Long> {

}
